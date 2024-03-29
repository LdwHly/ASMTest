package com.ldw.theme.view

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.util.Log
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.ldw.theme.R
import com.ldw.theme.api.IThemeChange
import com.ldw.theme.api.IThemeChange.Companion.ID
import com.ldw.theme.api.ThemeChangeManager
import java.lang.ref.WeakReference

open class TTextView : TextView, IThemeChange {
    private var mTextColorHintResId: Int = ID
    private var mTextColorResId: Int = ID

    init {
        Log.d("dd", "su")
    }

    constructor(context: Context) : this(context,null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {

        val a =
            context.obtainStyledAttributes(attrs, R.styleable.LTextAppearance, defStyleAttr, 0)
        if (a.hasValue(R.styleable.LTextAppearance_android_textColor)) {
            mTextColorResId =
                a.getResourceId(R.styleable.LTextAppearance_android_textColor, ID)
        }
        if (a.hasValue(R.styleable.LTextAppearance_android_textColorHint)) {
            mTextColorHintResId = a.getResourceId(
                R.styleable.LTextAppearance_android_textColorHint, ID
            )
        }
        ThemeChangeManager.map[context]?.field3?.add(WeakReference(this))
        a.recycle()
    }


//    override fun setTextColor(color: Int) {
//        super.setTextColor(color)
//        mTextColorResId = ID
//    }
//
//    override fun setTextColor(colors: ColorStateList?) {
//        super.setTextColor(colors)
//        mTextColorResId = ID
//    }

    override fun onThemeChange() {
        if (mTextColorResId != ID) {
            super.setTextColor(ContextCompat.getColor(context, mTextColorResId))
        }
        if (mTextColorHintResId != ID) {
            super.setHintTextColor(ContextCompat.getColor(context, mTextColorHintResId))
        }
    }

//    override fun setText(text: CharSequence?, type: BufferType?) {
//        super.setText(text, type)
//    }

}
