package com.example.cleanxyandroid.progressServiceActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import com.example.cleanxyandroid.R
import java.util.*
import kotlin.properties.Delegates

class TimerActivity : AppCompatActivity() {

    private var seconds : Int = 0
    private var running by Delegates.notNull<Boolean>()
    private var wasRunning : Boolean = true

    private lateinit var timerStat : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        supportActionBar?.hide()
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.appBlue)

        if (savedInstanceState != null ){
            seconds = savedInstanceState.getInt("seconds")
            running = savedInstanceState.getBoolean("running")
            wasRunning = savedInstanceState.getBoolean("wasRunning")
        }

        timerStat = findViewById(R.id.timerStatTimerActivity)

        onClickStart()

        runTimer()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("seconds", seconds)
        outState.putBoolean("running", running)
        outState.putBoolean("wasRunning", wasRunning)
    }

    override fun onPause() {
        super.onPause()
        wasRunning = running
        running = false
    }

    override fun onResume() {
        super.onResume()
        if (wasRunning) {
            running = true
        }
    }

    private fun onClickStart() {
        running = true
    }

    private fun onClickStop() {
        running = false
    }

    private fun onClickReset() {
        running = false
        seconds = 0
    }

    private fun runTimer() {
        val handler = Handler()
        handler.post(object : Runnable {
            override fun run() {
                val hours = seconds/1000
                val minutes = (seconds%3600) / 60
                val secs = seconds%60

                val time = String.format(Locale.getDefault(),"%d:%02d:%02d", hours, minutes, secs);

                timerStat.text = time

                if (running) {
                    seconds++
                }

                handler.postDelayed(this, 1000)
            }
        })
    }
}