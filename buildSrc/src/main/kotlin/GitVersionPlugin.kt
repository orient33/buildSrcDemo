import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File

class GitVersionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.register("getVersionProvider", GitVersionTask::class.java) {
            it.gitVersionFile.set(File(project.buildDir, "intermediates/gitVersionProvider/output"))

            it.outputs.upToDateWhen { false }
        }
    }

}