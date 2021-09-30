package com.example.cleanxyandroid.progressServiceActivities

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.cleanxyandroid.R
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.properties.Delegates


class TimerActivity : AppCompatActivity() {

    private var seconds : Int = 0
    private var running by Delegates.notNull<Boolean>()
    private var wasRunning : Boolean = true

    private lateinit var timerStat : TextView

    private lateinit var timerStopButton: ImageButton
    private lateinit var db : FirebaseFirestore

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

        db = FirebaseFirestore.getInstance()

        timerStopButton =findViewById(R.id.timerStopIconTimerActivity);

        timerStopButton.setOnClickListener {
//            Toast.makeText(applicationContext, "Under Progress", Toast.LENGTH_SHORT).show()

            val my_extras = intent.extras
            val ts1= my_extras?.getLong("timestamp")
            val ts = System.currentTimeMillis()

            val builder: AlertDialog.Builder = AlertDialog.Builder(this)


            builder.setMessage("Do you want to stop ?")

            // Set Alert Title

            // Set Alert Title
            builder.setTitle("Alert !")
            builder.setCancelable(false)

            // Set the positive button with yes name
            // OnClickListener method is use of
            // DialogInterface interface.


            // Set the positive button with yes name
            // OnClickListener method is use of
            // DialogInterface interface.
            builder
                .setPositiveButton(
                    "Yes",
                    DialogInterface.OnClickListener { dialog, which -> // When the user click yes button
                        val ts3= ts1?.let { it1 -> ts?.minus(it1) }// this gets the timestamp difference in milliSecond
                        Toast.makeText(applicationContext, "time stamp:"+ts+"\ntimestamp dfference:"+ts3, Toast.LENGTH_SHORT).show()


                        val tsArray = arrayListOf<Long>()
                        val timestamps : MutableList<Long> = tsArray.toMutableList()
                        if (ts1 != null) {
                            timestamps.add(ts1)
                        }
                        timestamps.add(ts)

//            saveBookingData(timestamps)
                        onPause()

//                        finish()
                    })

            builder
                .setNegativeButton(
                    "No",
                    DialogInterface.OnClickListener { dialog, which -> // If user click no
                        // then dialog box is canceled.
                        dialog.cancel()
                    })

            val alertDialog: AlertDialog = builder.create()

            alertDialog.show()



        }

    }


//    private fun saveBookingData(
//        timestamps: MutableList<Long>,
//    ) {
//        val bookingData = hashMapOf(
//            "timestamps" to timestamps,
//        )
//
//        db.collection("bookingAndroid").document(bid).set(bookingData)
//            .addOnCompleteListener {
//                if (it.isSuccessful) {
//                    startActivity(Intent(applicationContext, BookingSuccessfulActivity::class.java))
//                }
//                else {
//                    Toast.makeText(applicationContext, "Booking failed, Please try again", Toast.LENGTH_LONG).show()
//                }
//            }
//
//    }

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