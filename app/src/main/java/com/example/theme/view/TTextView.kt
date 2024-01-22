package com.example.theme.view

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import com.ldw.theme.api.IThemeChange

class TTextView : TextView, IThemeChange {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onThemeChange() {}
}
