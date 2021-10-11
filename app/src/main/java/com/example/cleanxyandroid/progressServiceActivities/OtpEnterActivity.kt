package com.example.cleanxyandroid.progressServiceActivities

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.cleanxyandroid.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class OtpEnterActivity : AppCompatActivity() {

    private lateinit var db : FirebaseFirestore
    private lateinit var auth : FirebaseAuth

    private lateinit var progressDialog : ProgressDialog

    private lateinit var confirmOtpBtn : Button
    private lateinit var otpField : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_enter)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        supportActionBar?.hide()
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.appBlue)

        db = Firebase.firestore
        auth = Firebase.auth

        progressDialog = ProgressDialog(this@OtpEnterActivity)
        progressDialog.setTitle("Checking for Details...")
        progressDialog.setMessage("Please Wait")
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)

        loadOtpOrNextActivity()

        confirmOtpBtn = findViewById(R.id.confirmOtpBtnOtpEnterActivity)
        otpField = findViewById(R.id.otpFieldOtpEnterActivity)
        confirmOtpBtn.setOnClickListener {
            checkOtpAndMoveForwardOrNot()
        }
    }

    private fun checkOtpAndMoveForwardOrNot() {
        progressDialog.show()
        val currentUser = auth.currentUser

        val otp = otpField.text.toString()
        if (otp.length < 4) {
            progressDialog.dismiss()
            Toast.makeText(applicationContext, "Please enter 4 digit OTP", Toast.LENGTH_SHORT).show()
        }
        else {
            db.collection("Ongoing").document(currentUser?.phoneNumber.toString()).get()
                .addOnSuccessListener {ongoingDoc ->
                    val bid = ongoingDoc.get("Booking Id") as String
                    db.collection("bookingAndroid").document(bid).get()
                        .addOnSuccessListener {bookingDoc ->
                            val otpByAdmin : Long? = bookingDoc.get("otp") as Long?
                            if (otpByAdmin == 0L) {
                                progressDialog.dismiss()
                                Toast.makeText(applicationContext, "Please wait for OTP to be updated", Toast.LENGTH_SHORT).show()
                            }
                            else {
                                val x = otpByAdmin.toString()
                                if (otp == x) {
                                    progressDialog.dismiss()
                                    startActivity(Intent(this@OtpEnterActivity, TimerActivity::class.java))
                                    finish()
                                }
                                else {
                                    progressDialog.dismiss()
                                    Toast.makeText(applicationContext, "Please enter the correct OTP", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                        .addOnFailureListener {
                            progressDialog.dismiss()
                            Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_SHORT).show()
                        }

                }
                .addOnFailureListener {
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, "Error to fetch details, please try again", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun loadOtpOrNextActivity() {
        progressDialog.show()

        val currentUser = auth.currentUser
        db.collection("Ongoing").document(currentUser?.phoneNumber.toString()).get()
            .addOnSuccessListener {
                val x = it.get("OTP") as Long?
                if (x == 0L) {
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, "OTP Yet to be entered", Toast.LENGTH_SHORT).show()
                }
                else {
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, "OTP Already entered", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@OtpEnterActivity, TimerActivity::class.java))
                    finish()
                }
            }
    }
}