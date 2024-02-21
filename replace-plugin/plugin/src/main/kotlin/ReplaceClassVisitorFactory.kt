import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.instrumentation.ClassContext
import com.android.build.api.instrumentation.ClassData
import com.android.build.api.instrumentation.InstrumentationParameters
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.Opcodes

abstract class ReplaceClassVisitorFactory : AsmClassVisitorFactory<InstrumentationParameters.None> {

    override fun createClassVisitor(
        classContext: ClassContext,
        nextClassVisitor: ClassVisitor
    ): ClassVisitor {
        return object : ClassVisitor(Opcodes.ASM5, nextClassVisitor) {
            val className = classContext.currentClassData.className

            // 这里，由于只需要修改方法，故而只重载了 visitMethod 找个方法
            override fun visit(
                version: Int,
                access: Int,
                name: String?,
                signature: String?,
                superName: String?,
                interfaces: Array<out String>?
            ) {
                println("className:$className superName:$superName")
                var newSuperName = superName
                if (superName == "androidx/appcompat/widget/AppCompatImageView" || superName == "android/widget/ImageView") {
                    newSuperName = "com/ldw/theme/view/TImageView"
                } else if (superName == "android/widget/TextView") {
                    newSuperName = "com/ldw/theme/view/TTextView"
                }
                super.visit(
                    version,
                    access,
                    name,
                    signature,
                    newSuperName,
                    interfaces
                )
            }
        }
    }

    override fun isInstrumentable(classData: ClassData): Boolean {
        val img = classData.className != "androidx.appcompat.widget.AppCompatImageView" && classData.className != "com.ldw.theme.view.TImageView" &&
                (classData.superClasses[0] == "android.widget.ImageView" ||
                        classData.superClasses[0] == "androidx.appcompat.widget.AppCompatImageView")
        val tv = classData.className != "com.ldw.theme.view.TTextView" && (classData.superClasses[0] == "android.widget.TextView")
//        if (classData.className.startsWith("androidx.appcompat.widget")) {
//            println("isInstrumentable className:${classData.className}")
//        }
        return img || tv
    }
}