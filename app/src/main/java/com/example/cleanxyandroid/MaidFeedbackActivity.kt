@file:Suppress("DEPRECATION")

package com.example.cleanxyandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat

class MaidFeedbackActivity : AppCompatActivity() {

    private lateinit var star1 : ImageView
    private lateinit var star2 : ImageView
    private lateinit var star3 : ImageView
    private lateinit var star4 : ImageView
    private lateinit var star5 : ImageView

    private lateinit var check1 : ImageView
    private lateinit var check2 : ImageView
    private lateinit var check3 : ImageView
    private lateinit var check4 : ImageView
    private lateinit var check5 : ImageView
    private lateinit var check6 : ImageView
    private lateinit var check7 : ImageView
    private lateinit var check8 : ImageView

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

        star1 = findViewById(R.id.star1MaidFeedbackActivity)
        star2 = findViewById(R.id.star2MaidFeedbackActivity)
        star3 = findViewById(R.id.star3MaidFeedbackActivity)
        star4 = findViewById(R.id.star4MaidFeedbackActivity)
        star5 = findViewById(R.id.star5MaidFeedbackActivity)

        star1.setOnClickListener {
            changeStarToYellow(star1)
            changeStarToWhite(star2)
            changeStarToWhite(star3)
            changeStarToWhite(star4)
            changeStarToWhite(star5)
        }

        star2.setOnClickListener {
            changeStarToYellow(star1)
            changeStarToYellow(star2)
            changeStarToWhite(star3)
            changeStarToWhite(star4)
            changeStarToWhite(star5)
        }

        star3.setOnClickListener {
            changeStarToYellow(star1)
            changeStarToYellow(star2)
            changeStarToYellow(star3)
            changeStarToWhite(star4)
            changeStarToWhite(star5)
        }

        star4.setOnClickListener {
            changeStarToYellow(star1)
            changeStarToYellow(star2)
            changeStarToYellow(star3)
            changeStarToYellow(star4)
            changeStarToWhite(star5)
        }

        star5.setOnClickListener {
            changeStarToYellow(star1)
            changeStarToYellow(star2)
            changeStarToYellow(star3)
            changeStarToYellow(star4)
            changeStarToYellow(star5)
        }

        check1 = findViewById(R.id.checkSq1MaidFeedbackActivity)
        check2 = findViewById(R.id.checkSq2MaidFeedbackActivity)
        check3 = findViewById(R.id.checkSq3MaidFeedbackActivity)
        check4 = findViewById(R.id.checkSq4MaidFeedbackActivity)
        check5 = findViewById(R.id.checkSq5MaidFeedbackActivity)
        check6 = findViewById(R.id.checkSq6MaidFeedbackActivity)
        check7 = findViewById(R.id.checkSq7MaidFeedbackActivity)
        check8 = findViewById(R.id.checkSq8MaidFeedbackActivity)

    }

    private fun changeStarToWhite(star: ImageView?) {
        star?.setImageDrawable(
            ContextCompat.getDrawable(
                applicationContext,
                R.drawable.ic_white_star_cxy
            )
        )
    }

    private fun changeStarToYellow(star: ImageView?) {
        star?.setImageDrawable(
            ContextCompat.getDrawable(
                applicationContext,
                R.drawable.ic_yellow_star_cxy
            )
        )
    }
}