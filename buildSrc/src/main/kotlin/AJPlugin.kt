import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.instrumentation.ClassContext
import com.android.build.api.instrumentation.ClassData
import com.android.build.api.instrumentation.FramesComputationMode
import com.android.build.api.instrumentation.InstrumentationParameters
import com.android.build.api.instrumentation.InstrumentationScope
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

abstract class AJPlugin : Plugin<Project> {

    var mProject: Project? = null

    override fun apply(project: Project) {
        mProject = project
        val androidComponents = project.extensions.getByType(AndroidComponentsExtension::class.java)

        androidComponents.onVariants { variant ->
            variant.transformClassesWith(
                ExampleClassVisitorFactory::class.java,
                InstrumentationScope.ALL
            ) {
                it.writeToStdout.set(false)
            }
            variant.setAsmFramesComputationMode(FramesComputationMode.COPY_FRAMES)
        }
    }

    interface ExampleParams : InstrumentationParameters {
        @get:Input
        val writeToStdout: Property<Boolean>
    }

    abstract class ExampleClassVisitorFactory :
        AsmClassVisitorFactory<ExampleParams> {

        override fun createClassVisitor(
            classContext: ClassContext,
            nextClassVisitor: ClassVisitor
        ): ClassVisitor {
            return VisitAJ(nextClassVisitor)
        }

        override fun isInstrumentable(classData: ClassData): Boolean {
            return classData.className.startsWith("com.example.AJ")
        }
    }

    class VisitAJ(nextCV: ClassVisitor) : ClassVisitor(Opcodes.ASM7, nextCV) {
        override fun visitMethod(
            access: Int,
            name: String?,
            descriptor: String?,
            signature: String?,
            exceptions: Array<out String>?
        ): MethodVisitor {
            val mv = super.visitMethod(access, name, descriptor, signature, exceptions)
            println("a=$access, name=$name, des=$descriptor, sig=$signature")
            if (access == 1 && name == "<init>" && descriptor == "()V") {
                return AddCode2Method(mv)
            }
            return mv
        }
    }

    class AddCode2Method(mv: MethodVisitor) : MethodVisitor(Opcodes.ASM7, mv) {

        val routerAndImpl: Set<Triple<String, String, String>> = setOf(
            Triple("trip1", "trip2", "trip3")
        )

        override fun visitInsn(opcode: Int) {
            println("visitInsn. $opcode")
            if (opcode == Opcodes.IRETURN || opcode == Opcodes.FRETURN || opcode == Opcodes.ARETURN
                || opcode == Opcodes.LRETURN || opcode == Opcodes.DRETURN || opcode == Opcodes.RETURN
            ) {
                //insert map key value
                routerAndImpl.forEach {
                    //
                    println("for each. $it")
                    mv.visitVarInsn(Opcodes.ALOAD, 0)
                    mv.visitFieldInsn(
                        Opcodes.GETFIELD, "com/example/AJ", "map", "Ljava/util/Map;"
                    )
                    mv.visitLdcInsn(it.first)
                    mv.visitLdcInsn(it.second)
                    println("visitMethodInsn 1")
                    mv.visitMethodInsn(
                        Opcodes.INVOKEINTERFACE,
                        "java/util/Map", "put",
                        "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", true
                    )
                    println("pop")
                    mv.visitInsn(Opcodes.POP)
                    println("end for $it")
                }
            }
            super.visitInsn(opcode)
        }
    }
}