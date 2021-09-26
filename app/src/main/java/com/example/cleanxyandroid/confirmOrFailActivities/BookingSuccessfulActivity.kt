package com.example.cleanxyandroid.confirmOrFailActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.cleanxyandroid.MainActivity
import com.example.cleanxyandroid.R

class BookingSuccessfulActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_successful)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        supportActionBar?.hide()
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.appBlue)

        val goBackText : TextView = findViewById(R.id.goBackTextBookingSuccessfulActivity)
        goBackText.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }

    }

    override fun onBackPressed() {
        Toast.makeText(applicationContext, "Disabled", Toast.LENGTH_SHORT).show()
    }
}