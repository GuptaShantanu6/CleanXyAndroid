package com.example.cleanxyandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*
import kotlin.properties.Delegates

class ScheduleSlotActivity : AppCompatActivity() {

    private var daySelected = 0
    private var monthSelected = 0
    private var yearSelected = 0

    private lateinit var hours : String
    private lateinit var minutes : String
    private lateinit var amOrPm : String

    private lateinit var amText : ImageView
    private lateinit var pmText : ImageView

    private lateinit var amPmView : View

    private var checkHour by Delegates.notNull<Int>()
    private var checkMinute by Delegates.notNull<Int>()

    private var timeErrType1 : Int = 0
    private var timeErrType2 : Int = 0

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

        val selectedServices = intent.getSerializableExtra("ss")

        val dtPickerDialog : DatePicker = findViewById(R.id.datePickerScheduleSlotActivity)
        dtPickerDialog.minDate = Calendar.getInstance().timeInMillis

        val now = System.currentTimeMillis() - 1000
        dtPickerDialog.maxDate = (now+(1000*60*60*24*3))

        val today = Calendar.getInstance()
        dtPickerDialog.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH)) {
                _, year, month, day ->
            daySelected = day
            monthSelected = month + 1
            yearSelected = year
        }

        val timeStatText : TextView = findViewById(R.id.timeDigTextScheduleSlotActivity)

        amText = findViewById(R.id.amViewScheduleSlotActivity)
        pmText = findViewById(R.id.pmViewScheduleSlotActivity)
        amPmView = findViewById(R.id.amPmViewScheduleSlotActivity)

        pmText.visibility = View.GONE

        minutes = "41"
        hours = "9"
        amOrPm = "am"

        val tp = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(9)
            .setMinute(41)
            .setTitleText("Select Slot Time")
            .build()

        tp.addOnPositiveButtonClickListener {
            checkHour = tp.hour
            checkMinute = tp.minute

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

        val confirmBtn : Button = findViewById(R.id.confirmSlotBtnScheduleSlotActivity)
        confirmBtn.setOnClickListener {
            if (checkIfTimeIsCorrect(checkHour, checkMinute)) {

                val fullTime = arrayOf(0,0,0,0,0,0)
                val h = hours.toInt()
                val m = minutes.toInt()
                fullTime[0] = h
                fullTime[1] = m
                if (amOrPm == "am") {
                    fullTime[2] = 1
                }
                else {
                    fullTime[2] = 2
                }
                fullTime[3] = daySelected
                fullTime[4] = monthSelected
                fullTime[5] = yearSelected

                val intent = Intent(this@ScheduleSlotActivity, BookingActivity::class.java)
                intent.putExtra("ss", selectedServices)
                intent.putExtra("fullTime", fullTime)

                startActivity(intent)
            }
            else {
                if (timeErrType1 == 1) {
                    Toast.makeText(applicationContext, "Schedule Services are only available from 8 Am to 6:30 Pm", Toast.LENGTH_SHORT).show()
                }
                else if (timeErrType2 == 1) {
                    Toast.makeText(applicationContext, "Schedule Services are only available 1 hour from now", Toast.LENGTH_SHORT).show()
                }
            }


            Log.d("Date Selected:","$daySelected/$monthSelected/$yearSelected")
        }
    }

    private fun checkIfTimeIsCorrect(hours: Int, minutes: Int): Boolean {
        val check: Boolean
        val c = Calendar.getInstance()
        val h = c.get(Calendar.HOUR)
        val m = c.get(Calendar.MINUTE)

        if (hours<8) {
            check = false
            timeErrType1 = 1
            timeErrType2 = 0
        }
        else if (hours>7) {
            check = false
            timeErrType1 = 1
            timeErrType2 = 0
        }
        else if (hours==6 && minutes>30) {
            check = false
            timeErrType1 = 1
            timeErrType2 = 0
        }
        else {
            if (hours<(h+1) && minutes<m) {
                check = false
                timeErrType2 = 1
                timeErrType1 = 0
            }
            else {
                check = true
                timeErrType1 = 0
                timeErrType2 = 0
            }
        }

        return true
    }
}
