package com.example.theme.api

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.TextView

class CTextView : TextView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        //        this(context,attrs,0);
        val su = javaClass.superclass.name
        Log.d("dd", su)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(text, type)
    }
}
