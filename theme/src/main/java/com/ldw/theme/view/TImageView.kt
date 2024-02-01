package com.ldw.theme.view

import android.content.Context
import android.content.pm.PackageInstaller.SessionInfo
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import com.ldw.theme.R
import com.ldw.theme.api.IThemeChange

class TImageView : ImageView, IThemeChange {
    constructor(context: Context?) : super(context)
    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        var a: TypedArray? = null
        try {
            a = context.obtainStyledAttributes(attrs, R.styleable.LBackground, defStyleAttr, 0)
            if (a.hasValue(R.styleable.LBackground_android_background)) {
                mBackgroundResId = a.getResourceId(
                    R.styleable.LBackground_android_background, SessionInfo.INVALID_ID
                )
            }
        } finally {
            a?.recycle()
        }
        try {
            a = context.obtainStyledAttributes(attrs, R.styleable.LImageView, defStyleAttr, 0)
            mSrcResId = a.getResourceId(R.styleable.LImageView_android_src, SessionInfo.INVALID_ID)
        } finally {
            a?.recycle()
        }
    }
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    private var mSrcResId = IThemeChange.ID
    private var mBackgroundResId = IThemeChange.ID

    override fun setBackgroundResource(resid: Int) {
        super.setBackgroundResource(resid)
        mBackgroundResId = resid
    }

    override fun setImageResource(resId: Int) {
        super.setImageResource(resId)
        mSrcResId = resId
    }

    override fun setBackground(background: Drawable) {
        super.setBackground(background)
        mBackgroundResId = IThemeChange.ID
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        mSrcResId = IThemeChange.ID
    }

    override fun setImageBitmap(bm: Bitmap) {
        super.setImageBitmap(bm)
        mSrcResId = IThemeChange.ID
    }

    override fun onThemeChange() {
        if (mSrcResId != IThemeChange.ID) {
            setImageResource(mSrcResId)
        }
        if (mBackgroundResId != IThemeChange.ID) {
            setBackgroundResource(mBackgroundResId)
        }
    }
}
