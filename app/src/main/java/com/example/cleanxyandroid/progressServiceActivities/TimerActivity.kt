package com.example.cleanxyandroid.progressServiceActivities

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
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

    private lateinit var db : FirebaseFirestore
    private lateinit var auth : FirebaseAuth

    private lateinit var timerStatText : TextView

    private var seconds by Delegates.notNull<Int>()

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

        progressDialog = ProgressDialog(this@TimerActivity)
        progressDialog.setTitle("Resuming Timer...")
        progressDialog.setMessage("Please Wait")
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)

        startStopwatchFromPreviousLeft()

        backBtn = findViewById(R.id.backBtnTimerActivity)
        backBtn.setOnClickListener {
            startActivity(Intent(this@TimerActivity, MainActivity::class.java))
        }

    }

    private fun startStopwatchFromPreviousLeft() {
        val currentUser = auth.currentUser
        progressDialog.show()

        db.collection("Ongoing").document(currentUser?.phoneNumber.toString()).get()
            .addOnSuccessListener {
                val started = it.get("Stopwatch Started") as Long?
                if (started == 0L) {
                    val startTime = System.currentTimeMillis()
                    val updateTime = hashMapOf(
                        "startTime" to startTime
                    )
                    db.collection("Ongoing").document(currentUser?.phoneNumber.toString()).update(
                        updateTime as Map<String, Any>
                    )
                        .addOnSuccessListener {
                            seconds = 0
                            resumeTimer(seconds)
                        }
                        .addOnFailureListener {
                            progressDialog.dismiss()
                            Toast.makeText(applicationContext, "Error, Please restart the application", Toast.LENGTH_SHORT).show()
                        }

                }
            }
            .addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "Error, Please restart the application", Toast.LENGTH_SHORT).show()
            }
    }

    private fun resumeTimer(s: Int) {
        val handler = Handler()
        handler.post {
            kotlin.run {
                var hours = s / 3600
                var minutes = (s%3600)/60
                var secs = s % 60

                var time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs)

                timerStatText.text = time

                seconds++

//                Handler yet to be made
//                handler.postDelayed(this@TimerActivity, 1000)
            }
        }
    }
}