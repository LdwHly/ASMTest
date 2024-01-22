package com.example.theme.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import com.ldw.theme.api.IThemeChange
import com.ldw.theme.api.IThemeChange.Companion.ID

class TTextView : TextView, IThemeChange {
    private var mTextColorHintResId: Int = ID
    private var mTextColorResId: Int = ID

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {

        val a =
            context!!.obtainStyledAttributes(attrs, R.styleable.LTextAppearance, defStyleAttr, 0)
        if (a.hasValue(R.styleable.LTextAppearance_android_textColor)) {
            mTextColorResId =
                a.getResourceId(R.styleable.LTextAppearance_android_textColor, ID)
        }
        if (a.hasValue(R.styleable.LTextAppearance_android_textColorHint)) {
            mTextColorHintResId = a.getResourceId(
                R.styleable.LTextAppearance_android_textColorHint, ID
            )
        }
        a.recycle()
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun setTextColor(color: Int) {
        super.setTextColor(color)
        mTextColorResId = ID
    }
    override fun onThemeChange() {
        if (mTextColorResId != ID) {
            super.setTextColor(ContextCompat.getColor(context, mTextColorResId))
        }
    }
}
