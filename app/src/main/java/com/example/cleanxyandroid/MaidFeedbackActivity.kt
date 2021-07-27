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

    private lateinit var checkStar1 : ImageView
    private lateinit var checkStar2 : ImageView
    private lateinit var checkStar3 : ImageView
    private lateinit var checkStar4 : ImageView
    private lateinit var checkStar5 : ImageView
    private lateinit var checkStar6 : ImageView
    private lateinit var checkStar7 : ImageView
    private lateinit var checkStar8 : ImageView

    private lateinit var q1o1Text : TextView
    private lateinit var q1o2Text : TextView
    private lateinit var q1o3Text : TextView
    private lateinit var q1o4Text : TextView
    private lateinit var q2o1Text : TextView
    private lateinit var q2o2Text : TextView
    private lateinit var q2o3Text : TextView
    private lateinit var q2o4Text : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maid_feedback)

        supportActionBar?.hide()
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.appBlue)

        val backBtn : ImageView = findViewById(R.id.backBtnMaidFeedbackActivity)
        backBtn.setOnClickListener {
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

        checkStar1 = findViewById(R.id.checkSq1MaidFeedbackActivity)
        checkStar2 = findViewById(R.id.checkSq2MaidFeedbackActivity)
        checkStar3 = findViewById(R.id.checkSq3MaidFeedbackActivity)
        checkStar4 = findViewById(R.id.checkSq4MaidFeedbackActivity)
        checkStar5 = findViewById(R.id.checkSq5MaidFeedbackActivity)
        checkStar6 = findViewById(R.id.checkSq6MaidFeedbackActivity)
        checkStar7 = findViewById(R.id.checkSq7MaidFeedbackActivity)
        checkStar8 = findViewById(R.id.checkSq8MaidFeedbackActivity)

        checkStar1.visibility = View.GONE
        checkStar2.visibility = View.GONE
        checkStar3.visibility = View.GONE
        checkStar4.visibility = View.GONE
        checkStar5.visibility = View.GONE
        checkStar6.visibility = View.GONE
        checkStar7.visibility = View.GONE
        checkStar8.visibility = View.GONE

        q1o1Text = findViewById(R.id.q1o1TextViewMaidFeedbackActivity)
        q1o2Text = findViewById(R.id.q1o2TextViewMaidFeedbackActivity)
        q1o3Text = findViewById(R.id.q1o3TextViewMaidFeedbackActivity)
        q1o4Text = findViewById(R.id.q1o4TextViewMaidFeedbackActivity)
        q2o1Text = findViewById(R.id.q2o1TextViewMaidFeedbackActivity)
        q2o2Text = findViewById(R.id.q2o2TextViewMaidFeedbackActivity)
        q2o3Text = findViewById(R.id.q2o3TextViewMaidFeedbackActivity)
        q2o4Text = findViewById(R.id.q2o4TextViewMaidFeedbackActivity)

        q1o1Text.setOnClickListener {
            checkStar1.visibility = View.VISIBLE
            checkStar2.visibility = View.GONE
            checkStar3.visibility = View.GONE
            checkStar4.visibility = View.GONE
        }

        q1o2Text.setOnClickListener {
            checkStar1.visibility = View.GONE
            checkStar2.visibility = View.VISIBLE
            checkStar3.visibility = View.GONE
            checkStar4.visibility = View.GONE
        }

        q1o3Text.setOnClickListener {
            checkStar1.visibility = View.GONE
            checkStar2.visibility = View.GONE
            checkStar3.visibility = View.VISIBLE
            checkStar4.visibility = View.GONE
        }
        q1o4Text.setOnClickListener {
            checkStar1.visibility = View.GONE
            checkStar2.visibility = View.GONE
            checkStar3.visibility = View.GONE
            checkStar4.visibility = View.VISIBLE
        }
        q2o1Text.setOnClickListener {
            checkStar5.visibility = View.VISIBLE
            checkStar6.visibility = View.GONE
            checkStar7.visibility = View.GONE
            checkStar8.visibility = View.GONE
        }

        q2o2Text.setOnClickListener {
            checkStar5.visibility = View.GONE
            checkStar6.visibility = View.VISIBLE
            checkStar7.visibility = View.GONE
            checkStar8.visibility = View.GONE
        }

        q2o3Text.setOnClickListener {
            checkStar5.visibility = View.GONE
            checkStar6.visibility = View.GONE
            checkStar7.visibility = View.VISIBLE
            checkStar8.visibility = View.GONE
        }

        q2o4Text.setOnClickListener {
            checkStar5.visibility = View.GONE
            checkStar6.visibility = View.GONE
            checkStar7.visibility = View.GONE
            checkStar8.visibility = View.VISIBLE
        }

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