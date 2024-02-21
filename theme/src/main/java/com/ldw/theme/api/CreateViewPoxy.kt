package com.ldw.theme.api

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ldw.theme.view.TButton
import com.ldw.theme.view.TImageView
import com.ldw.theme.view.TTextView

class CreateViewPoxy(private val mDelegate: ICreateView) : ICreateView {

    override fun realCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        var view = mDelegate.realCreateView(parent, name, context, attrs)
        if (view == null) {
            when (name) {
                "TextView" -> view = TTextView(context, attrs)
                "ImageView" -> view = TImageView(context, attrs)
                "androidx.appcompat.widget.AppCompatImageView" -> view = TImageView(context, attrs)
                "Button" -> view = TButton(context, attrs)
            }
        }
        return view
    }
}