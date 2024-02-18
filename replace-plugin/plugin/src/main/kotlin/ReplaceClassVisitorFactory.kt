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
                println("className:$className superName:$superName interfaces:${interfaces?.isNotEmpty()?.let {
                    interfaces[0] 
                }}")
                var newList: Array<out String>? = interfaces
                interfaces?.isNotEmpty()?.let {
                    if (!interfaces.contains("com/ldw/theme/api/IThemeChange")) {
                        val temp = interfaces.toMutableList()
                        temp.add("com/ldw/theme/api/IThemeChange")
                        newList = temp.toTypedArray()
                    }
                }
                super.visit(
                    version,
                    access,
                    name,
                    signature,
                    "com/ldw/theme/view/TImageView",
                    newList
                )
            }
        }
    }

    override fun isInstrumentable(classData: ClassData): Boolean {
        println("isInstrumentable:${classData.className} ")
        return classData.className != "com.ldw.theme.view.TImageView" && classData.superClasses[0] == "android.widget.ImageView"
    }
}