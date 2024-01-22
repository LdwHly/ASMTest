package com.example.theme.view

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import com.example.theme.api.IThemeChange

class TButton : Button, IThemeChange {
    constructor(context: Context?) : super(context)

    @JvmOverloads
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int = 0) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onThemeChange() {}
}
