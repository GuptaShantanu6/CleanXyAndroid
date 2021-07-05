package com.example.cleanxyandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class Login : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    private var storedVerificationId : String = ""
    private lateinit var resendToken : PhoneAuthProvider.ForceResendingToken
    private lateinit var callBacks : PhoneAuthProvider.OnVerificationStateChangedCallbacks

    private lateinit var phoneNumberFromField : String
    private lateinit var newPhoneNumber : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val goToSignUpText : TextView = findViewById(R.id.login_has_no_acc)
        goToSignUpText.setOnClickListener {
            startActivity(Intent(this@Login, Register::class.java))
        }

        val phoneNumberText : TextInputEditText = findViewById(R.id.login_phno)

        val otpSendBtn : TextView = findViewById(R.id.login_sendOTP_tv)
        otpSendBtn.setOnClickListener {
            phoneNumberFromField = phoneNumberText.text.toString()
            Toast.makeText(this, "OTP has been sent", Toast.LENGTH_SHORT).show()
            logIn(phoneNumberFromField)
        }

        callBacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
//                Toast.makeText(this@Login, "OTP has been sent", Toast.LENGTH_SHORT).show()
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Toast.makeText(this@Login, "Unable to Send OTP, please try again later", Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                storedVerificationId = verificationId
                resendToken = token

                Toast.makeText(this@Login, "Please enter the OTP", Toast.LENGTH_SHORT).show()

            }

        }

        val otpField : TextInputEditText = findViewById(R.id.login_otp)

        val logInBtn : Button = findViewById(R.id.loginBtn)
        logInBtn.setOnClickListener {

            val otpEntered : String = otpField.text.toString()

            if (otpEntered.isEmpty()) {
                Toast.makeText(this, "Please enter the OTP", Toast.LENGTH_SHORT).show()
            }
            else {
                val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(
                    storedVerificationId, otpEntered)

                signInWithPhoneAuthCredential(credential)
            }
        }

    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(this, "Wrong OTP entered", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(this, "OTP Error, please try again later", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun logIn(phoneNumber: String) {
//        this.phoneNumber = "+91$phoneNumber"
        newPhoneNumber = "+91$phoneNumber"
        Log.d("Shantanu", newPhoneNumber)

        sendVerificationCode(newPhoneNumber)
    }

    private fun sendVerificationCode(phoneNumber: String) {

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callBacks)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)

    }


}