import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

abstract class CustomTask : DefaultTask() {
    @TaskAction
    fun myAction() {
        println("CustomTask.myAction. print! ${project.name} ${project.parent?.name}")
    }

}