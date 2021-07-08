@file:Suppress("DEPRECATION")

package com.example.cleanxyandroid.tabSplashFragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.cleanxyandroid.MainActivity
import com.example.cleanxyandroid.R
import com.google.android.material.tabs.TabLayout
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.TimeUnit

class RegisterFragment : Fragment() {

    private lateinit var auth : FirebaseAuth

    private lateinit var name : EditText
    private lateinit var email : EditText
    private lateinit var phoneNumber : EditText
    private lateinit var otp : EditText

    private var nameFromField : String = ""
    private var emailFromField : String = ""
    private var phoneNumberFromField : String = ""
    private var otpFromField : String = ""

    private var newPhoneNumberFromField : String = ""

    private lateinit var db : FirebaseFirestore

    private var storedVerificationId : String = ""
    private lateinit var resendToken : PhoneAuthProvider.ForceResendingToken
    private lateinit var callBacks : PhoneAuthProvider.OnVerificationStateChangedCallbacks

    private lateinit var progressDialog : ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        auth = FirebaseAuth.getInstance()

        db = FirebaseFirestore.getInstance()

        val getOtpBtnSignUpRegisterSplash : TextView = view.findViewById(R.id.getOtpViewSignUpRegisterSplash)
        getOtpBtnSignUpRegisterSplash.setOnClickListener {
            phoneNumber = view.findViewById(R.id.mobileRegisterSplash)
            phoneNumberFromField = phoneNumber.text.toString()

            checkIfPhoneNumberExists()

        }

        progressDialog = ProgressDialog(activity)
        progressDialog.setTitle("Signing you up")
        progressDialog.setMessage("Please Wait")
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)

        callBacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {

            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Toast.makeText(activity, "Unable to send OTP, Please try again later", Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                storedVerificationId = verificationId
                resendToken = token

                Toast.makeText(activity, "OTP has been sent", Toast.LENGTH_SHORT).show()
            }

        }

        val signInBtn : Button = view.findViewById(R.id.signInBtnRegisterSplash)
        signInBtn.setOnClickListener {

            name = view.findViewById(R.id.nameRegisterSplash)
            email = view.findViewById(R.id.emailRegisterSplash)
            otp = view.findViewById(R.id.otpRegisterSplash)

            nameFromField = name.text.toString()
            emailFromField = email.text.toString()
            otpFromField = otp.text.toString()

            when {
                TextUtils.isEmpty(nameFromField) -> name.error = "Name is not correct"
                TextUtils.isEmpty(emailFromField) -> email.error = "Email is not correct"
                phoneNumberFromField.length!=10 -> phoneNumber.error = "Phone number is not correct"
                TextUtils.isEmpty(otpFromField) -> otp.error = "OTP cannot be empty"

                else -> {

                    progressDialog.show()

                    val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(
                        storedVerificationId,
                        otpFromField
                    )

                    signInWithPhoneAuthCredential(credential)

                }
            }

        }

        val host : TabLayout = requireActivity().findViewById(R.id.tabSplash)

        val alreadyAccText : TextView = view.findViewById(R.id.alreadyAccountTextSignUpRegisterSplash)
        alreadyAccText.setOnClickListener {
            host.getTabAt(0)?.select()
        }

        return view

    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {

        val userData = hashMapOf(
            "name" to nameFromField,
            "email" to emailFromField,
            "phoneNumber" to newPhoneNumberFromField
        )

        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    db.collection("customerAndroid").document(newPhoneNumberFromField).set(userData)
                        .addOnCompleteListener { saveTask ->
                            if (saveTask.isSuccessful) {
                                progressDialog.dismiss()
                                startActivity(Intent(activity, MainActivity::class.java))
                                activity?.finish()
                            }
                            else {
                                Toast.makeText(activity, "Unable to save data, Please try again", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
                else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(activity, "Wrong OTP entered, Please try again", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(activity, "OTP error, please try again", Toast.LENGTH_SHORT).show()
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

    private fun checkIfPhoneNumberExists() {

        when {
            TextUtils.isEmpty(phoneNumberFromField) -> phoneNumber.error = "Phone Number cannot be empty"
            phoneNumberFromField.length != 10 -> phoneNumber.error = "Only enter the 10 digit Phone Number"

            else -> {

                newPhoneNumberFromField = "+91$phoneNumberFromField"

                val docRef = db.collection("customerAndroid").document(newPhoneNumberFromField)
                docRef.get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val doc = task.result
                        if (doc != null) {
                            if (doc.exists()) {
                                Toast.makeText(activity, "Phone number already exists, Please login", Toast.LENGTH_SHORT).show()
                            } else {
                                sendVerificationCode()
                            }
                        }
                        else {
                            sendVerificationCode()
                        }
                    }
                    else {
                        Toast.makeText(activity, "Failed with ${task.exception}", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }

    }

}