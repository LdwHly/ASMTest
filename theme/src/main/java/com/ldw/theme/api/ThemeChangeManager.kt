package com.ldw.theme.api

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import androidx.core.view.LayoutInflaterCompat
import java.lang.ref.WeakReference
import java.util.concurrent.ConcurrentHashMap

object ThemeChangeManager {

    val map =
        ConcurrentHashMap<Context, DataBean3<LayoutInflater, LayoutInflater, ArrayList<WeakReference<IThemeChange>>>>()
    private var replaceAll = false

    fun init(context: Application, iCreateView: ICreateView, replaceAll: Boolean) {
        this.replaceAll = replaceAll
        val createViewPoxy = CreateViewPoxy(iCreateView)
        context.registerActivityLifecycleCallbacks(object :
            Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                from(activity, createViewPoxy)
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
        from(context, createViewPoxy)
        context.registerComponentCallbacks(object : ComponentCallbacks {
            override fun onConfigurationChanged(newConfig: Configuration) {
                val currentNightMode = newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK
                Log.i("ThemeChangeManager","currentNightMode=$currentNightMode lastNightMode=$lastNightMode")
                if (currentNightMode != lastNightMode) {
                    lastNightMode = currentNightMode
                    h.removeCallbacksAndMessages(null)
                    h.postDelayed( {
                        for (key in map.keys) {
                            map[key]?.field3?.filter { it.get() != null }?.map { it.get() }?.forEach {
                                it?.onThemeChange()
                            }
                        }
                    },500)
                }
            }

            override fun onLowMemory() {}
        })
    }

    var lastNightMode = 0
    var h = Handler(Looper.getMainLooper())

    private fun from(context: Context, iCreateView: ICreateView) {
        var bean3 = map[context]
        if (bean3 == null) {
            bean3 =
                DataBean3<LayoutInflater, LayoutInflater, ArrayList<WeakReference<IThemeChange>>>().apply {
                    field3 = ArrayList()
                }
            val src = LayoutInflater.from(context)

            val factory2: LayoutInflater.Factory2 =
                Factory2Poxy(src.factory2, bean3.field3, iCreateView)

            bean3.field1 = src.cloneInContext(context).apply {
                LayoutInflaterCompat.setFactory2(this, factory2)
            }

            if (replaceAll) {
                if (src.factory2 == null) {
                    LayoutInflaterCompat.setFactory2(src, factory2)
                } else if (src.factory2 !is Factory2Poxy) {
                    ReflectUtils.setFieldValue(src, "mFactory2", factory2)
                    ReflectUtils.setFieldValue(src, "mFactory", factory2)
                }
            }
            bean3.field2 = src
            map[context] = bean3
        }
    }

    fun from(context: Context): LayoutInflater {
        return map[context]?.field1 ?: LayoutInflater.from(context)
    }
}