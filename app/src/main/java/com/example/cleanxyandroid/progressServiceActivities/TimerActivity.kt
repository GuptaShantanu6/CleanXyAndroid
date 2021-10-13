package com.example.cleanxyandroid.progressServiceActivities

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.cleanxyandroid.MainActivity
import com.example.cleanxyandroid.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.properties.Delegates

class TimerActivity : AppCompatActivity() {

    private lateinit var backBtn : ImageView
    private lateinit var progressDialog : ProgressDialog
    private lateinit var stopTimerProgressDialog : ProgressDialog

    private lateinit var db : FirebaseFirestore
    private lateinit var auth : FirebaseAuth

    private lateinit var timerStatText : TextView
    private lateinit var timerStopIcon : ImageView

    private var seconds by Delegates.notNull<Int>()
    private var timeDiff by Delegates.notNull<Long>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        supportActionBar?.hide()
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.appBlue)

        db = Firebase.firestore
        auth = Firebase.auth

        timerStatText = findViewById(R.id.timerStatTimerActivity)
        timerStopIcon = findViewById(R.id.timerStopIconTimerActivity)

        progressDialog = ProgressDialog(this@TimerActivity)
        progressDialog.setTitle("Resuming Timer...")
        progressDialog.setMessage("Please Wait")
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)

        stopTimerProgressDialog= ProgressDialog(this@TimerActivity)
        stopTimerProgressDialog.setTitle("Collecting Details...")
        stopTimerProgressDialog.setMessage("Please Wait")
        stopTimerProgressDialog.setCancelable(false)
        stopTimerProgressDialog.setCanceledOnTouchOutside(false)

        startStopwatchFromPreviousLeft()

        backBtn = findViewById(R.id.backBtnTimerActivity)
        backBtn.setOnClickListener {
            startActivity(Intent(this@TimerActivity, MainActivity::class.java))
        }

        val alertBuilder = AlertDialog.Builder(this@TimerActivity)
        alertBuilder.setTitle("Alert")
        alertBuilder.setMessage("Are you sure to stop the stopwatch ?")
        alertBuilder.setCancelable(false)

        alertBuilder.setPositiveButton("Yes") { _, _ ->
            saveTimeDiffAndProceedToPay()
        }
        alertBuilder.setNegativeButton("No") { _, _ ->
            //Resume Timer
        }

        timerStopIcon.setOnClickListener {
            alertBuilder.show()
        }

    }

    private fun saveTimeDiffAndProceedToPay() {
        val currentUser = auth.currentUser
        stopTimerProgressDialog.show()
        timerStatText.visibility = View.GONE

        db.collection("Ongoing").document(currentUser?.phoneNumber.toString()).get()
            .addOnSuccessListener {
                val bid = it.get("Booking Id") as String

                val startTime = it.get("startTime") as Long
                val endTime = System.currentTimeMillis()
                val timeDiff = (endTime-startTime)/1000

                val updatedOngoing = hashMapOf(
                    "Stopwatch Stopped" to 1,
                    "endTime" to System.currentTimeMillis(),
                    "ongoing" to 0
                )

                db.collection("Ongoing").document(currentUser?.phoneNumber.toString()).update(
                    updatedOngoing as Map<String, Any>
                )
                    .addOnSuccessListener {
                        val updatedBookingRef = hashMapOf(
                            "Booking Duration" to timeDiff,
                        )
                        db.collection("bookingAndroid").document(bid).update(updatedBookingRef as Map<String, Any>)
                            .addOnSuccessListener {
                                stopTimerProgressDialog.dismiss()
                                startActivity(Intent(this@TimerActivity, PaymentActivity::class.java))
                            }
                            .addOnFailureListener {
                                stopTimerProgressDialog.dismiss()
                                Toast.makeText(applicationContext, "Error, Please restart the application", Toast.LENGTH_SHORT).show()
                            }
                    }
                    .addOnFailureListener {
                        stopTimerProgressDialog.dismiss()
                        Toast.makeText(applicationContext, "Error, Please restart the application", Toast.LENGTH_SHORT).show()
                    }
            }
            .addOnFailureListener {
                stopTimerProgressDialog.dismiss()
                Toast.makeText(applicationContext, "Error, Please restart the application", Toast.LENGTH_SHORT).show()
            }
    }

    private fun startStopwatchFromPreviousLeft() {
        val currentUser = auth.currentUser
        progressDialog.show()

        db.collection("Ongoing").document(currentUser?.phoneNumber.toString()).get()
            .addOnSuccessListener {
                val ifStarted = it.get("Stopwatch Started") as Long
                val ifEnded = it.get("Stopwatch Stopped") as Long

                when {
                    ifStarted == 0L -> {
                        val startTime = System.currentTimeMillis()
                        val updateTime = hashMapOf(
                            "startTime" to startTime,
                            "Stopwatch Started" to 1L
                        )
                        db.collection("Ongoing").document(currentUser?.phoneNumber.toString()).update(
                            updateTime as Map<String, Any>
                        )
                            .addOnSuccessListener {
                                progressDialog.dismiss()
                                seconds = 0
                                resumeTimer()
                            }
                            .addOnFailureListener {
                                progressDialog.dismiss()
                                Toast.makeText(applicationContext, "Error, Please restart the application", Toast.LENGTH_SHORT).show()
                            }

                    }
                    ifEnded == 0L -> {
                        progressDialog.dismiss()
                        val currentTime = System.currentTimeMillis()
                        val startTime = it.get("startTime") as Long
                        timeDiff = (currentTime-startTime)/1000

                        seconds = timeDiff.toInt()
                        resumeTimer()

                    }
                    else -> {
                        progressDialog.dismiss()
                        Toast.makeText(applicationContext, "Booking Already finished", Toast.LENGTH_SHORT).show()
                    }
                }

            }
            .addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "Error, Please restart the application", Toast.LENGTH_SHORT).show()
            }
    }

    private fun resumeTimer() {
        val handler = Handler()
        handler.post(object : Runnable {
            override fun run() {
                val hours = seconds / 3600
                val minutes = (seconds%3600)/60
                val secs = seconds % 60

                val time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs)

                timerStatText.text = time

                seconds++
                handler.postDelayed(this, 1000)
            }
        })
    }
}