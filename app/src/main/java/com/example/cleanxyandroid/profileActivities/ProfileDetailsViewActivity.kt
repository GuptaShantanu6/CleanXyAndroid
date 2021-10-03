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

    private val TAG = "ProfileDetailsViewActivity"
    private lateinit var auth: FirebaseAuth;


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

        val editBtn: TextView = findViewById(R.id.profileEditBtnProfileDetailsActivity)
        editBtn.setOnClickListener {
            startActivity(Intent(this, ProfileNewDetailsActivity::class.java))
        }

        val backBtn: ImageView = findViewById(R.id.backBtnProfileDetailsViewActivity)
        backBtn.setOnClickListener {
            onBackPressed()
        }


    }

    @SuppressLint("LongLogTag")
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        val db = Firebase.firestore

        val em: TextView = findViewById(R.id.profile_view_email_text)
        val phonetext: TextView = findViewById(R.id.profile_phone_text)
        val name_text: TextView = findViewById(R.id.profile_name)
        val add: TextView = findViewById(R.id.address_profile)

        if (currentUser != null) {
            db.collection("users").document(currentUser.phoneNumber.toString()).get()
                .addOnSuccessListener { documentSnapshot ->
                    em.text = documentSnapshot.get("email") as String
                    phonetext.text = documentSnapshot.get("phone") as String
                    name_text.text = documentSnapshot.get("name") as String
                    add.text = documentSnapshot.get("address") as String
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }
        }

    }
}