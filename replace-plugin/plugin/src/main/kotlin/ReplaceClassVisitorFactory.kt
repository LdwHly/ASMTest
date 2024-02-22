import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.instrumentation.ClassContext
import com.android.build.api.instrumentation.ClassData
import com.android.build.api.instrumentation.InstrumentationParameters
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
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
                if (superName == "android/widget/ImageView") {
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

            override fun visitMethod(
                access: Int,
                name: String?,
                descriptor: String?,
                signature: String?,
                exceptions: Array<out String>?
            ): MethodVisitor {
                println("visitMethod ${classContext.currentClassData.superClasses[0]} name:${name} descriptor:$descriptor signature:$signature exceptions:${exceptions?.toMutableList().toString()}")
                val mv = super.visitMethod(access, name, descriptor, signature, exceptions)
                if (name == "<init>") {
                    if (classContext.currentClassData.superClasses[0] == "android.widget.TextView") {
                        return EditTextViewMethodVisitor(api, mv)
                    } else if (classContext.currentClassData.superClasses[0] == "android.widget.ImageView"){
                        return EditImageViewMethodVisitor(api, mv)
                    }
                }
                return mv
            }

            override fun visitEnd() {
                super.visitEnd()
            }
        }
    }

    override fun isInstrumentable(classData: ClassData): Boolean {
//        println("我的:${classData.className}")
        val img = kotlin.run {
//            classData.className != "androidx.appcompat.widget.AppCompatImageView" && classData.className != "com.ldw.theme.view.TImageView" &&
//                    (classData.superClasses[0] == "android.widget.ImageView" ||
//                            classData.superClasses[0] == "androidx.appcompat.widget.AppCompatImageView")
            classData.className != "com.ldw.theme.view.TImageView" && classData.superClasses[0] == "android.widget.ImageView"
        }
        val tv = classData.className != "com.ldw.theme.view.TTextView" && (classData.superClasses[0] == "android.widget.TextView")
        return img || tv
    }

}