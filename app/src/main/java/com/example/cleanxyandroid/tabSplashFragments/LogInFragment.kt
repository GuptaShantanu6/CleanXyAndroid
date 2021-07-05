package com.example.cleanxyandroid.tabSplashFragments

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.cleanxyandroid.MainActivity
import com.example.cleanxyandroid.R
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.TimeUnit

class LogInFragment : Fragment(){

    private lateinit var auth : FirebaseAuth

    private var storedVerificationId : String = ""
    private lateinit var resendToken : PhoneAuthProvider.ForceResendingToken
    private lateinit var callBacks : PhoneAuthProvider.OnVerificationStateChangedCallbacks

    private lateinit var phoneNumber : EditText
    private var phoneNumberFromField : String = ""

    private lateinit var otp : EditText
    private lateinit var otpFromField : String

    private lateinit var newPhoneNumberFromField : String

    private lateinit var db : FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_log_in, container, false)

        auth = FirebaseAuth.getInstance()

        db = FirebaseFirestore.getInstance()

        phoneNumber = view.findViewById(R.id.loginPhoneNumberFieldSplash)

        val otpSendBtn : View = view.findViewById(R.id.otpSendLoginSplashBtn)
        otpSendBtn.setOnClickListener {
            Toast.makeText(activity, "Otp btn has been clicked", Toast.LENGTH_SHORT).show()
            phoneNumberFromField = phoneNumber.text.toString()
            checkIfPhoneNumberExists()
        }

        callBacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {

            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Toast.makeText(activity, "Unable to send the OTP, please try again later", Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                storedVerificationId = verificationId
                resendToken = token

                Toast.makeText(activity, "Please enter the OTP", Toast.LENGTH_SHORT).show()

            }

        }

        val loginBtn : Button = view.findViewById(R.id.loginBtnRegisterSplash)
        loginBtn.setOnClickListener {
            otp = view.findViewById(R.id.otpFieldRegisterSplash)
            otpFromField = otp.text.toString()

            when {
                TextUtils.isEmpty(phoneNumberFromField) -> phoneNumber.error = "Phone number cannot be empty"
                TextUtils.isEmpty(otpFromField) -> otp.error = "OTP cannot be empty"

                else -> {
                    val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(
                        storedVerificationId, otpFromField
                    )

                    signInWithPhoneAuthCredential(credential)

                }
            }

        }

        return  view
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(activity, MainActivity::class.java))
                    activity?.finish()
                }
                else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(activity, "Wrong OTP entered", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(activity, "OTP Error, please try again later", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun checkIfPhoneNumberExists() {

        when {
            TextUtils.isEmpty(phoneNumberFromField) -> phoneNumber.error = "Phone number cannot be empty"

            else -> {
                newPhoneNumberFromField = "+91$phoneNumberFromField"

                val docRef = db.collection("customerAndroid").document(newPhoneNumberFromField)
                docRef.get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val doc : DocumentSnapshot? = task.result
                        if (doc != null) {
                            if (doc.exists()) {
                                Log.d("phoneStatus", "Phone number exists, OTP has been sent.")
                                Toast.makeText(activity, "Phone number exists, OTP has been sent", Toast.LENGTH_SHORT).show()

                                sendVerificationCode()

                            } else {
                                Log.d("phoneStatus", "Phone number does not exist, please Sign up first.")
                                Toast.makeText(activity, "Phone number does not exist, please Sign up first.", Toast.LENGTH_SHORT).show()
                            }
                        }
                        else {
                            Log.d("phoneStatus", "Phone number does not exist, please Sign up first.")
                            Toast.makeText(activity, "Phone number does not exist, please Sign up first.", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else {
                        Log.d("Failed With", task.exception.toString())
                    }
                }
            }
        }

    }

    private fun sendVerificationCode() {

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(newPhoneNumberFromField)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(callBacks)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)

    }

}