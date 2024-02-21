package com.example.theme

import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.theme.api.NetworkUtils
import com.ldw.theme.api.IThemeChange
import com.ldw.theme.view.TTextView

class MainActivity : AppCompatActivity() {
    var tvRoot: LinearLayout? = null

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        tvRoot = findViewById(R.id.tv_root)
        LayoutInflater.from(this).inflate(R.layout.main_test, tvRoot, true)
            .findViewById<ImageView>(R.id.iv_icon)
            .setImageResource(R.drawable.img_card_training_fail_1)

        initText()
        NetworkUtils.registerNetConnection(application, NetWork {
            initText()
        })
    }

    private fun initText() {
        val tvTest: TextView? = tvRoot?.findViewById<TextView>(R.id.tv_test)
        tvRoot?.post {
            var info = "";
            tvRoot?.findViewById<View>(R.id.iv_root)?.let { ivRoot ->
                info =
                    "root:${tvRoot?.width}  iv:${ivRoot.width} " +
                            "screen:${resources.displayMetrics.widthPixels} netWork:${
                                NetworkUtils.isNetSystemUsable(
                                    application
                                )
                            }"
            }
            if (tvTest is IThemeChange) {
                tvTest.text = info
                tvTest.onThemeChange()
            }

        }
    }

    private class NetWork(val call: () -> Unit) : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            call.invoke()
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            call.invoke()
        }

    }

}
