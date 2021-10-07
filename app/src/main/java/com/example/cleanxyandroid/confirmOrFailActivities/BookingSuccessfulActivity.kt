package com.example.cleanxyandroid.confirmOrFailActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
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

class BookingSuccessfulActivity : AppCompatActivity() {

    private lateinit var db : FirebaseFirestore
    private lateinit var goBackText : TextView

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_successful)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        supportActionBar?.hide()
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.appBlue)

        auth = Firebase.auth

        goBackText = findViewById(R.id.goBackTextBookingSuccessfulActivity)
        goBackText.visibility = View.GONE

        db = Firebase.firestore

        val bid : String? = intent.getSerializableExtra("bid") as String?

        saveBookingDataInOngoingCollection(bid)

        val goBackText : TextView = findViewById(R.id.goBackTextBookingSuccessfulActivity)
        goBackText.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }

    }

    private fun saveBookingDataInOngoingCollection(bid: String?) {
        val currentUser = auth.currentUser

        val ongoingData = hashMapOf(
            "Booking Id" to bid,
            "ongoing" to 1,
            "OTP" to 0
        )

        db.collection("Ongoing").document(currentUser?.phoneNumber.toString()).set(ongoingData)
            .addOnCompleteListener {
                goBackText.visibility = View.VISIBLE
            }
    }

    override fun onBackPressed() {
        Toast.makeText(applicationContext, "Disabled", Toast.LENGTH_SHORT).show()
    }
}