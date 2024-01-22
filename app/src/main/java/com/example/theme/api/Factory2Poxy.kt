package com.example.theme.api

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater.Factory2
import android.view.View
import com.example.theme.view.TButton
import com.example.theme.view.TImageView
import com.example.theme.view.TTextView
import java.lang.ref.WeakReference

class Factory2Poxy(
    private var delegate: Factory2?,
    private val obverse: MutableList<WeakReference<IThemeChange>>
) : Factory2 {
    init {
        if (delegate == null) {
            delegate = object : Factory2 {
                override fun onCreateView(
                    parent: View?,
                    name: String,
                    context: Context,
                    attrs: AttributeSet
                ): View? {
                    return realCreateView(parent, name, context, attrs)
                }

                override fun onCreateView(
                    name: String,
                    context: Context,
                    attrs: AttributeSet
                ): View? {
                    return onCreateView(
                        null, name, context, attrs
                    )
                }
            }
        }
    }

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        val view = realCreateView(parent, name, context, attrs)
        return view ?: delegate!!.onCreateView(parent, name, context, attrs)
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return onCreateView(null, name, context, attrs)
    }

    private fun realCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        var view: View? = null
        when (name) {
            "TextView" -> view = TTextView(context, attrs)
            "ImageView" -> view = TImageView(context, attrs)
            "Button" -> view = TButton(context, attrs)
        }
        if (view is  IThemeChange ) {
            obverse.add(WeakReference(view))
        }
        return view
    }
}
