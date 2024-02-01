package com.example.theme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        val tvRoot: LinearLayout = findViewById(R.id.tv_root)
        LayoutInflater.from(this.applicationContext).inflate(R.layout.main_test, tvRoot, true)
            .findViewById<ImageView>(R.id.iv_icon)
            .setImageResource(R.drawable.img_card_training_fail_1)

        val tvTest: TextView = tvRoot.findViewById<TextView>(R.id.tv_test)
        tvRoot.post {
            var info = "";
            tvRoot.findViewById<View>(R.id.iv_root)?.let { ivRoot ->
                info =
                    "root:${tvRoot.width}  iv:${ivRoot.width} screenW:${resources.displayMetrics.widthPixels}"
            }
            tvTest.text = info
        }

    }

}
