import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class CustomPlugin2 : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.register("hello") {
            it.doFirst {
                println("CustomPlugin2.apply. first. do")
            }
            it.doLast {
                println("CustomPlugin2.apply. last. do")
            }
        }
        println("CustomPlugin2. apply.. 14 line")

        val extension = project.extensions.getByName("androidComponents")
        if (extension == null) return
        else if (extension is ApplicationAndroidComponentsExtension) {
            extension.beforeVariants { variantBuilder ->
                if (variantBuilder.name == "staging") {
                    variantBuilder.enableUnitTest = false
                    variantBuilder.minSdk = 23
                }
            }
            extension.finalizeDsl { ext ->
                ext.buildTypes.create("staging").let { buildType ->
                    buildType.initWith(ext.buildTypes.getByName("debug"))
                    buildType.manifestPlaceholders["hostName"] = "internal.example.com"
                    buildType.applicationIdSuffix = ".debugStaging"
                    // 在后面解释 beforeVariants 时添加了本行代码。
                    buildType.isDebuggable = true
                }
            }
        }
    }
}