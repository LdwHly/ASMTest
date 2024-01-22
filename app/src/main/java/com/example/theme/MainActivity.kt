package com.example.theme

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        val tvRoot: LinearLayout = findViewById(R.id.tv_root)
        LayoutInflater.from(this).inflate(R.layout.main_test, tvRoot, true)
            .findViewById<ImageView>(R.id.iv_icon)
            .setImageResource(R.drawable.img_card_training_fail_1)
    }

}
