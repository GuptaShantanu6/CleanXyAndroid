package com.example.cleanxyandroid

import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.TimePicker
import kotlin.math.min

class ScheduleSlotActivity : AppCompatActivity() {

    private var currentMaid : Int = 1
    private var currentHour : Int = 1

    private lateinit var timePicker : TimePickerDialog
    private lateinit var hours : String
    private lateinit var minutes : String
    private lateinit var amOrPm : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_slot)

        supportActionBar?.hide()
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.appBlue)

        val backBtn : ImageView = findViewById(R.id.backBtnScheduleSlotActivity)
        backBtn.setOnClickListener {
            onBackPressed()
        }

        val maidPlusBtn : ImageView = findViewById(R.id.maidPlusScheduleSlotActivity)
        val maidMinusBtn : ImageView = findViewById(R.id.maidMinusScheduleSlotActivity)
        val hourPlusBtn : ImageView = findViewById(R.id.hourPlusScheduleSlotActivity)
        val hourMinusBtn : ImageView = findViewById(R.id.hourMinusScheduleSlotActivity)

        val maidStatText : TextView = findViewById(R.id.maidStatTextScheduleSlotActivity)
        val hourStatText : TextView = findViewById(R.id.hourStatTextScheduleSlotActivity)

        maidMinusBtn.setOnClickListener {
            if (currentMaid > 1) {
                maidStatText.text = (--currentMaid).toString()
            }
        }

        maidPlusBtn.setOnClickListener {
            if (currentMaid < 5) {
                maidStatText.text = (++currentMaid).toString()
            }
        }

        hourMinusBtn.setOnClickListener {
            if (currentHour > 1) {
                hourStatText.text = (--currentHour).toString()
            }
        }

        hourPlusBtn.setOnClickListener {
            if (currentHour < 5) {
                hourStatText.text = (++currentHour).toString()
            }
        }

        val timeStatText : TextView = findViewById(R.id.timeDigTextScheduleSlotActivity)

        val timePickerDialogListener : TimePickerDialog.OnTimeSetListener =
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->

                minutes = if (minute < 10 ) {
                    "0$minute"
                } else  {
                    minute.toString()
                }

                when {
                    hourOfDay == 0 -> {
                        hours = (12+hourOfDay).toString()
                        amOrPm =  "am"
                    }
                    hourOfDay > 12 -> {
                        hours = (hourOfDay-12).toString()
                        amOrPm = "pm"
                    }
                    hourOfDay == 12 -> {
                        hours = hourOfDay.toString()
                        amOrPm = "pm"
                    }
                    else -> {
                        hours = hourOfDay.toString()
                        amOrPm = "am"
                    }
                }

                timeStatText.text = getString(R.string.new_time, hours, minutes)

            }

        timeStatText.setOnClickListener {
            timePicker = TimePickerDialog(
                this@ScheduleSlotActivity,
                timePickerDialogListener,
                12,
                10,
                false
            )

            timePicker.show()

        }

    }
}