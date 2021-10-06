package com.example.cleanxyandroid.profileActivities

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.cleanxyandroid.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import de.hdodenhof.circleimageview.CircleImageView

@Suppress("DEPRECATION")
class ProfileNewDetailsActivity : AppCompatActivity() {

    private val pickImage = 100
    private var imageUri : Uri? = null

    private lateinit var pfpView : CircleImageView

    private lateinit var newName : EditText
    private lateinit var newEmail : EditText
    private lateinit var newAddress : EditText

    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    private lateinit var db : FirebaseFirestore
    private lateinit var progressDialog : ProgressDialog
    private lateinit var auth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_new_details)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        supportActionBar?.hide()
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.appBlue)

        auth = Firebase.auth

        pfpView = findViewById(R.id.pfpViewNewDetProfileActivity)

        db = FirebaseFirestore.getInstance()

        val selectImgBtn : ImageView = findViewById(R.id.imageSelectBtnNewDetProfActivity)
        selectImgBtn.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

        progressDialog = ProgressDialog(this@ProfileNewDetailsActivity)
        progressDialog.setTitle("Saving New Details...")
        progressDialog.setMessage("Please Wait")
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)

        newName = findViewById(R.id.nameProfileNewDetailsActivity)
        newEmail = findViewById(R.id.emailProfileNewDetailsActivity)
        newAddress = findViewById(R.id.addressProfileNewDetailsActivity)

        showInfoOnEditTexts()

        val saveBtn : Button = findViewById(R.id.saveBtnProfileNewDetailsActivity)
        saveBtn.setOnClickListener {

            var nameSave = newName.text.toString()
            val emailSave = newEmail.text.toString()
            val addressSave = newAddress.text.toString()

            nameSave = nameSave.trim()

            checkInfoEntered(nameSave, emailSave, addressSave)
        }

    }

    private fun showInfoOnEditTexts() {
        val currentUser = auth.currentUser
        db.collection("customerAndroid").document(currentUser?.phoneNumber.toString()).get()
            .addOnSuccessListener {
                newName.setText(it.get("name") as String?)
                newEmail.setText(it.get("email") as String?)
                newAddress.setText(it.get("address") as String?)
            }
    }

    private fun checkInfoEntered(nameSave: String, emailSave: String,  addressSave: String) {
        if (nameSave.isNotEmpty() && checkName(nameSave)) {
            if (emailSave.isNotEmpty()) {
                if (addressSave.isNotEmpty()) {
                    progressDialog.show()
                    saveUserInfo(nameSave, emailSave, addressSave)
                }
                else {
                    Toast.makeText(applicationContext, "Enter a valid address", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                Toast.makeText(applicationContext, "Enter a valid email", Toast.LENGTH_SHORT).show()
            }
        }
        else {
            Toast.makeText(applicationContext, "Enter a valid name", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveUserInfo(nameSave: String, emailSave: String, addressSave: String) {

        val currentUser = auth.currentUser

        val newData = hashMapOf(
            "name" to nameSave,
            "email" to emailSave,
            "address" to addressSave,
            "phoneNumber" to currentUser?.phoneNumber.toString()
        )

        db.collection("customerAndroid").document(currentUser?.phoneNumber.toString()).set(newData)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    progressDialog.dismiss()
                    startActivity(Intent(this@ProfileNewDetailsActivity, ProfileDetailsViewActivity::class.java))
                    finish()
                }
                else {
                    Toast.makeText(applicationContext, "Please try again later", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun checkName(name : String) : Boolean {
        for (x in name) {
            if (x != ' ' && x !in 'A'..'Z' && x !in 'a'..'z') {
                return false
            }
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            pfpView.setImageURI(imageUri)
        }
    }
}