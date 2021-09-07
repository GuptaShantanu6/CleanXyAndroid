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

        val servicesSelected = intent.getSerializableExtra("ss")

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
            if (checkIfTimeIsCorrect(hours, amOrPm)) {
                val intent = Intent(this@ScheduleSlotActivity, BookingActivity::class.java)
                intent.putExtra("ss", servicesSelected)
                intent.putExtra("daySelected", daySelected)
                intent.putExtra("monthSelected", monthSelected)
                intent.putExtra("yearSelected", yearSelected)
                intent.putExtra("hourSelected", hours)
                intent.putExtra("minuteSelected", minutes)
                intent.putExtra("amOrPm", amOrPm)

                startActivity(intent)
            }
            else {
                Toast.makeText(this, "Please Select the time between 7 AM and 8 PM", Toast.LENGTH_SHORT).show()
            }


            Log.d("Date Selected:","$daySelected/$monthSelected/$yearSelected")
        }
    }

    private fun checkIfTimeIsCorrect(hours: String, amOrPm: String): Boolean {
        var check = true

        if (amOrPm == "am") {
            val tempHour = hours.toInt()
            if (tempHour < 7) {
                check = false
            }
        }
        else {
            val tempHour = hours.toInt()
            if (tempHour != 12 && tempHour >= 8) {
                check = false
            }
        }

        return check
    }
}
