package com.example.cleanxyandroid

import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

class ScheduleSlotActivity : AppCompatActivity() {

    private var currentMaid : Int = 1
    private var currentHour : Int = 1

    private lateinit var timePicker : TimePickerDialog
    private lateinit var hours : String
    private lateinit var minutes : String
    private lateinit var amOrPm : String

    private lateinit var amText : ImageView
    private lateinit var pmText : ImageView

    private lateinit var amPmView : View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_slot)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        supportActionBar?.hide()
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.appBlue)

        val backBtn : ImageView = findViewById(R.id.backBtnScheduleSlotActivity)
        backBtn.setOnClickListener {
            onBackPressed()
        }

        val timeStatText : TextView = findViewById(R.id.timeDigTextScheduleSlotActivity)

        amText = findViewById(R.id.amViewScheduleSlotActivity)
        pmText = findViewById(R.id.pmViewScheduleSlotActivity)
        amPmView = findViewById(R.id.amPmViewScheduleSlotActivity)

        pmText.visibility = View.GONE

        minutes = ""
        hours = ""
        amOrPm = ""

        val tp = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(9)
            .setMinute(41)
            .setTitleText("Select Slot Time")
            .build()

        tp.addOnPositiveButtonClickListener {
            minutes = if (tp.minute < 10) {
                '0' + tp.minute.toString()
            } else {
                tp.minute.toString()
            }

            val h = tp.hour

            when {
                h == 0 -> {
                    hours = (12+h).toString()
                    amOrPm = "am"
                }
                h > 12 -> {
                    hours = (h-12).toString()
                    amOrPm = "pm"
                }
                h == 12 -> {
                    hours = h.toString()
                    amOrPm = "pm"
                }
                else -> {
                    hours = h.toString()
                    amOrPm = "am"
                }
            }

            timeStatText.text = getString(R.string.new_time, hours, minutes)

            if (amOrPm == "am") {
                amText.visibility = View.VISIBLE
                pmText.visibility = View.GONE
            }
            else {
                amText.visibility = View.GONE
                pmText.visibility = View.VISIBLE
            }
        }

        tp.addOnCancelListener {
            tp.dismiss()
        }

        tp.addOnDismissListener {
            tp.dismiss()
        }

        tp.addOnNegativeButtonClickListener {
            tp.dismiss()
        }

        timeStatText.setOnClickListener {
            tp.show(supportFragmentManager, "Hello Calendar")
        }

        amText.setOnClickListener {
            tp.show(supportFragmentManager, "Hello Calendar")
        }

        pmText.setOnClickListener {
            tp.show(supportFragmentManager, "Hello Calendar")
        }

        amPmView.setOnClickListener {
            tp.show(supportFragmentManager, "Hello Calendar")
        }
    }
}