import org.objectweb.asm.MethodVisitor

class EditTextViewMethodVisitor(api: Int, methodVisitor: MethodVisitor?) :
    MethodVisitor(api, methodVisitor) {
    override fun visitMethodInsn(
        opcode: Int,
        owner: String?,
        name: String?,
        descriptor: String?,
        isInterface: Boolean
    ) {
        if ("android/widget/TextView" == owner && "<init>" == name) {
            super.visitMethodInsn(
                opcode,
                "com/ldw/theme/view/TTextView",
                name,
                descriptor,
                isInterface
            )
        } else {
            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
        }
    }
}