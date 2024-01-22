package com.example.theme.api

import android.app.Activity
import android.app.Application
import android.content.Context
import android.view.View
import androidx.startup.Initializer
import com.example.theme.view.TButton
import com.example.theme.view.TImageView
import com.example.theme.view.TTextView
import com.ldw.theme.api.ThemeChangeManager

class ThemeChangeInitialzer : Initializer<Unit> {

    override fun create(context: Context) {
        (context as? Application)?.let { application ->
            ThemeChangeManager.init(application) { parent, name, context, attrs ->
                var view: View? = null
//                if (context !is Activity) {
                when (name) {
                    "TextView" -> view = TTextView(context, attrs)
                    "ImageView" -> view = TImageView(context, attrs)
                    "Button" -> view = TButton(context, attrs)
                }
//                }
                view
            }
        }
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}