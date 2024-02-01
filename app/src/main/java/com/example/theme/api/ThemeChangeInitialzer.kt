package com.example.theme.api

import android.app.Application
import android.content.Context
import androidx.startup.Initializer
import com.ldw.theme.api.ThemeChangeManager

class ThemeChangeInitialzer : Initializer<Unit> {

    override fun create(context: Context) {
        (context as? Application)?.let { application ->
            ThemeChangeManager.init(application) { parent, name, context, attrs ->
              null
            }
        }
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}