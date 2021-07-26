@file:Suppress("DEPRECATION")

package com.example.cleanxyandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView

class MaidFeedbackActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maid_feedback)

        supportActionBar?.hide()
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.appBlue)

        val backbtn : ImageView = findViewById(R.id.backBtnMaidFeedbackActivity)
        backbtn.setOnClickListener {
            onBackPressed()
        }

        val q2MainText : TextView = findViewById(R.id.q2MainTextMaidFeedbackActivity)
        val q1MainText : TextView = findViewById(R.id.q1MainTextMaidFeedbackActivity)

        q2MainText.isSelected = true
        q1MainText.isSelected = true

    }
}