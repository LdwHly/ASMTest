package com.example.theme.api

import android.app.Application
import android.content.Context
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.startup.Initializer
import com.ldw.theme.view.TButton
import com.ldw.theme.view.TImageView
import com.ldw.theme.view.TTextView
import com.ldw.theme.api.ThemeChangeManager

class ThemeChangeInitialzer : Initializer<Unit> {

    override fun create(context: Context) {
        (context as? Application)?.let { application ->
            ThemeChangeManager.init(application) { parent, name, context, attrs ->
                var view: View? = null
                if (context !is AppCompatActivity) {
                    when (name) {
                        "TextView" -> view = TTextView(context, attrs)
                        "ImageView" -> view = TImageView(context, attrs)
                        "Button" -> view = TButton(context, attrs)
                    }
                }
                view
            }
        }
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}