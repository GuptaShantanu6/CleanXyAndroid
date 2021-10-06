package com.example.cleanxyandroid.profileActivities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import com.example.cleanxyandroid.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Suppress("DEPRECATION")
class ProfileDetailsViewActivity : AppCompatActivity() {

    private val TAG = "ProfileDetViewActivity"
    private lateinit var auth: FirebaseAuth

    private lateinit var em : TextView
    private lateinit var phone_text : TextView
    private lateinit var name_text : TextView
    private lateinit var address : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_details_view)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        auth = Firebase.auth

        supportActionBar?.hide()
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.appBlue)

        em = findViewById(R.id.profile_view_email_text)
        phone_text = findViewById(R.id.profile_phone_text)
        name_text = findViewById(R.id.profile_name)
        address = findViewById(R.id.address_profile)

        profileDetailsLoaded()

        val editBtn: TextView = findViewById(R.id.profileEditBtnProfileDetailsActivity)
        editBtn.setOnClickListener {
            startActivity(Intent(this, ProfileNewDetailsActivity::class.java))
        }

        val backBtn: ImageView = findViewById(R.id.backBtnProfileDetailsViewActivity)
        backBtn.setOnClickListener {
            onBackPressed()
        }

    }

    private fun profileDetailsLoaded() {
        val currentUser = auth.currentUser
        val db = Firebase.firestore

        if (currentUser != null) {
            db.collection("customerAndroid").document(currentUser.phoneNumber.toString()).get()
                .addOnSuccessListener { documentSnapshot ->
                    em.text = documentSnapshot.get("email") as String?
                    phone_text.text = documentSnapshot.get("phoneNumber") as String?
                    name_text.text = documentSnapshot.get("name") as String?
                    address.text = documentSnapshot.get("address") as String
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }
        }
    }

}