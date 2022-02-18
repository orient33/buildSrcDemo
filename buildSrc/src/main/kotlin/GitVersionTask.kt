import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

abstract class GitVersionTask : DefaultTask() {

    @get:OutputFile
    abstract val gitVersionFile: RegularFileProperty

    @TaskAction
    fun getGitVersion() {
//        val process = ProcessBuilder("git", "rev-parse --short HEAD").start()
//        val error = process.errorStream.readBytes().toString()
//        if (error.isNotBlank()) {
//            System.err.println("fail get GitVersion. $error")
//        }
//            val version = process.inputStream.readBytes().toString()
            gitVersionFile.get().asFile.writeText("129")
    }
}