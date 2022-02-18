import com.android.build.api.artifact.SingleArtifact
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File
import com.android.build.api.variant.AndroidComponentsExtension

class ExamplePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val gitVersionProvider =
            project.tasks.register("gitVersionProvider", GitVersionTask::class.java) {
                it.gitVersionFile.set(
                    File(project.buildDir, "intermediates/gitVersionProvider/output")
                )
                it.outputs.upToDateWhen { false }
            }

        val androidComponents = project.extensions.getByType(AndroidComponentsExtension::class.java)

        androidComponents.onVariants { variant ->

            val manifestUpdater =
                project.tasks.register(
                    variant.name + "ManifestUpdater",
                    ManifestUpdateTask::class.java
                ) {
                    it.gitInfoFile.set(gitVersionProvider.flatMap(GitVersionTask::gitVersionFile))
                }
            variant.artifacts.use(manifestUpdater)
                .wiredWithFiles(
                    ManifestUpdateTask::mergedManifest,
                    ManifestUpdateTask::outManifest
                )
                .toTransform(SingleArtifact.MERGED_MANIFEST)

//            project.tasks.register(variant.name + "Verifier", VerifyManifestTask::class.java) {
//                it.apkFolder.set(variant.artifacts.get(SingleArtifact.APK))
//                it.builtArtifactsLoader.set(variant.artifacts.getBuiltArtifactsLoader())
//            }
        }
    }
}