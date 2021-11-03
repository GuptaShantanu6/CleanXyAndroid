package com.example.cleanxyandroid.profileActivities

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import com.example.cleanxyandroid.MainActivity
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

    private lateinit var progressDialog : ProgressDialog


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

        progressDialog = ProgressDialog(this@ProfileDetailsViewActivity)
        progressDialog.setTitle("Loading Details...")
        progressDialog.setMessage("Please Wait")
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)

        profileDetailsLoaded()

        val editBtn: TextView = findViewById(R.id.profileEditBtnProfileDetailsActivity)
        editBtn.setOnClickListener {
            startActivity(Intent(this, ProfileNewDetailsActivity::class.java))
        }

        val backBtn: ImageView = findViewById(R.id.backBtnProfileDetailsViewActivity)
        backBtn.setOnClickListener {
            startActivity(Intent(this@ProfileDetailsViewActivity, MainActivity::class.java))
        }

    }

    @SuppressLint("SetTextI18n")
    private fun profileDetailsLoaded() {
        val currentUser = auth.currentUser
        val db = Firebase.firestore

        progressDialog.show()

        if (currentUser != null) {
            db.collection("customerAndroid").document(currentUser.phoneNumber.toString()).get()
                .addOnSuccessListener { documentSnapshot ->
                    em.text = documentSnapshot.get("email") as String?
                    phone_text.text = documentSnapshot.get("phoneNumber") as String?
                    name_text.text = documentSnapshot.get("name") as String?

                    val addressComp = documentSnapshot.get("addressCompleted") as Long?
                    if (addressComp == 1L) {
                        val house = documentSnapshot.get("houseNo") as String?
                        val apartmentOrRoad = documentSnapshot.get("apartmentOrRoad") as String?
                        val city = documentSnapshot.get("city") as String?
                        val pincode = documentSnapshot.get("pincode") as String?

                        address.text = "$house, $apartmentOrRoad, $city, $pincode"
                        progressDialog.dismiss()
                    }

                }
                .addOnFailureListener { exception ->
                    progressDialog.dismiss()
                    Log.w(TAG, "Error getting documents.", exception)
                }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@ProfileDetailsViewActivity, MainActivity::class.java))
    }

}