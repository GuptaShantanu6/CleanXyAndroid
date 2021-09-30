package com.example.cleanxyandroid.progressServiceActivities

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.cleanxyandroid.R

class OtpEnterActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_enter)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        supportActionBar?.hide()
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.appBlue)

        val confirmOtpBtn : Button = findViewById(R.id.confirmOtpBtnOtpEnterActivity)
        confirmOtpBtn.setOnClickListener {
//            Toast.makeText(applicationContext, "Under Progress", Toast.LENGTH_SHORT).show()

//            val tsLong = System.currentTimeMillis() / 1000
            val ts = System.currentTimeMillis()
            Toast.makeText(applicationContext, "time stamp:"+ts, Toast.LENGTH_SHORT).show()

            val intent=Intent(this,TimerActivity::class.java).apply {
                putExtra("timestamp",ts)
            }
            startActivity(intent)
        }

    }


}