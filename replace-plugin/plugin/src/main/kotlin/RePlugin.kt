import com.android.build.api.instrumentation.FramesComputationMode
import com.android.build.api.instrumentation.InstrumentationScope
import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class RePlugin : Plugin<Project> {
    companion object {
        private const val TAG = "RePlugin"
    }

    override fun apply(target: Project) {
        val androidComponentsExtension =
            target.extensions.getByType(AndroidComponentsExtension::class.java)
        androidComponentsExtension.onVariants { variant ->
            log("variant: ${variant.name}")
            variant.instrumentation.apply {
                transformClassesWith(ReplaceClassVisitorFactory::class.java, InstrumentationScope.ALL) {}
                setAsmFramesComputationMode(FramesComputationMode.COMPUTE_FRAMES_FOR_INSTRUMENTED_METHODS)
            }
        }
    }

    private fun log(msg: String) {
        println("[$TAG]: $msg")
    }
}