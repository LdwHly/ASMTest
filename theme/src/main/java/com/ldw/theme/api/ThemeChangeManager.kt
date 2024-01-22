package com.ldw.theme.api

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.LayoutInflaterCompat
import java.lang.ref.WeakReference
import java.util.concurrent.ConcurrentHashMap

object ThemeChangeManager {
    private val map = ConcurrentHashMap<Context, List<WeakReference<IThemeChange>>>()

    fun init(context: Application, iCreateView: ICreateView) {
        context.registerActivityLifecycleCallbacks(object :
            Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                from(activity, iCreateView)
            }

            override fun onActivityStarted(activity: Activity) {}
            override fun onActivityResumed(activity: Activity) {}
            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
            override fun onActivityDestroyed(activity: Activity) {
                map.remove(activity)
            }
        })
        from(context, iCreateView)
        context.registerComponentCallbacks(object : ComponentCallbacks {
            override fun onConfigurationChanged(newConfig: Configuration) {
                if (newConfig.uiMode != Configuration.UI_MODE_NIGHT_UNDEFINED) {
                    for (key in map.keys) {
                        map[key]?.filter { it.get() != null }?.map { it.get() }?.forEach {
                            it?.onThemeChange()
                        }
                    }
                }
            }

            override fun onLowMemory() {}
        })
    }


    private fun from(context: Context, iCreateView: ICreateView) {
        var list = map[context]
        if (list == null) {
            list = ArrayList()
            val src = LayoutInflater.from(context)
            val factory2: LayoutInflater.Factory2 = Factory2Poxy(src.factory2, list, iCreateView)
            if (src.factory2 == null) {
                LayoutInflaterCompat.setFactory2(src, factory2)
            } else if (src.factory2 !is Factory2Poxy) {
                ReflectUtils.setFieldValue(src, "mFactory2", factory2)
                ReflectUtils.setFieldValue(src, "mFactory", factory2)
            }
            map[context] = list
        }
    }
}