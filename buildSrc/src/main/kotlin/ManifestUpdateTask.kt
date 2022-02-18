import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

abstract class ManifestUpdateTask : DefaultTask() {

    @get:InputFile
    abstract val gitInfoFile: RegularFileProperty

    @get:InputFile
    abstract val mergedManifest: RegularFileProperty

    @get:OutputFile
    abstract val outManifest: RegularFileProperty

    @TaskAction
    fun updateVersionName() {
        val ver = gitInfoFile.get().asFile.readText()
        var manifest = mergedManifest.get().asFile.readText()
        manifest = manifest.replace(
            "android:versionCode=\"1\"",
            "android:versionCode=\"${ver}\""
        )
        println("updateManifest. Writes to " + outManifest.get().asFile.absolutePath)
        outManifest.asFile.get().writeText(manifest)
    }
}