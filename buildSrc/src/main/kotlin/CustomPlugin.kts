import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

class CustomPlugin: Plugin<Project> {
    override fun apply(project: Project) {
        project.plugins.withType(AppPlugin::class.java) {
            val extension = project.extensions.getByName("androidComponents") as ApplicationAndroidComponentsExtension
            extension.beforeVariants {
                // disable all unit tests for apps (only using instrumentation tests)
                it.enableUnitTest = false
            }
        }
        project.plugins.withType(LibraryPlugin::class.java) {
            val extension = project.extensions.getByName("androidComponents") as LibraryAndroidComponentsExtension
            extension.beforeVariants(extension.selector().withBuildType("debug")) {
                // Disable instrumentation for debug
                it.enableAndroidTest = false
            }
            extension.beforeVariants(extension.selector().withBuildType("release")) {
                // disable all unit tests for apps (only using instrumentation tests)
                it.enableUnitTest = false
            }
        }
    }
}