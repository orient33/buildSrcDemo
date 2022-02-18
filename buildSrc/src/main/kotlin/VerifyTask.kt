import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.TaskAction
import java.io.ByteArrayOutputStream
import java.io.PrintStream

import com.android.build.api.variant.BuiltArtifactsLoader
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Internal
import java.io.File

//访问构建产物，并检查清单文件是否正确更新。
abstract class VerifyTask : DefaultTask() {

    @get:InputFiles
    abstract val apkFolder: DirectoryProperty

    @get:Internal
    abstract val builtArtifactsLoader: Property<BuiltArtifactsLoader>

    @TaskAction
    fun taskAction() {
        val builtArtifacts = builtArtifactsLoader.get().load(apkFolder.get())
            ?: throw RuntimeException("Cannot load APKs")
        if (builtArtifacts.elements.size != 1)
            throw RuntimeException("Expected one APK !")
        val apk = File(builtArtifacts.elements.single().outputFile).toPath()
        println("Insert code to verify manifest file in $apk")
        println("SUCCESS")
    }
}