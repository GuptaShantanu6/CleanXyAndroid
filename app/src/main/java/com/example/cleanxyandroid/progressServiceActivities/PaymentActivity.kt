package com.example.cleanxyandroid.progressServiceActivities

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanxyandroid.MainActivity
import com.example.cleanxyandroid.R
import com.example.cleanxyandroid.adapters.ServicePaymentAdapter
import com.example.cleanxyandroid.model.ServiceInfoForPayment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.properties.Delegates

class PaymentActivity : AppCompatActivity() {

    private lateinit var db : FirebaseFirestore
    private lateinit var auth : FirebaseAuth

    private var serviceRecyclerView : RecyclerView? = null
    private var serviceAdapter : ServicePaymentAdapter? = null
    private var sService : MutableList<ServiceInfoForPayment>? = null

    private lateinit var amountStat : TextView
    private var price : Long = 0
    private lateinit var bid : String

    private lateinit var progressDialog : ProgressDialog
    private lateinit var alertBuilder : AlertDialog.Builder
    private lateinit var completeProgressDialog : ProgressDialog

    private lateinit var backBtn : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        supportActionBar?.hide()
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.appBlue)

        db = Firebase.firestore
        auth = Firebase.auth

        amountStat = findViewById(R.id.amountStatPaymentActivity)
        backBtn = findViewById(R.id.backBtnPaymentActivity)

        serviceRecyclerView = findViewById(R.id.servicesRecyclerViewPaymentActivity)
        serviceRecyclerView?.setHasFixedSize(true)

        val linearLayoutManager = LinearLayoutManager(baseContext)
        serviceRecyclerView!!.layoutManager = linearLayoutManager

        sService = ArrayList()
        serviceAdapter = baseContext?.let { ServicePaymentAdapter(it, false, sService as ArrayList<ServiceInfoForPayment>) }
        serviceRecyclerView?.adapter = serviceAdapter

        progressDialog = ProgressDialog(this@PaymentActivity)
        progressDialog.setTitle("Loading Details...")
        progressDialog.setMessage("Please Wait")
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)

        completeProgressDialog = ProgressDialog(this@PaymentActivity)
        completeProgressDialog.setTitle("Finishing Booking...")
        completeProgressDialog.setMessage("Please Wait")
        completeProgressDialog.setCancelable(false)
        completeProgressDialog.setCanceledOnTouchOutside(false)

        loadRecyclerViewItems()

        backBtn.setOnClickListener {
            backFuncPaymentActivity()
        }

        alertBuilder = AlertDialog.Builder(this@PaymentActivity)
        alertBuilder.setTitle("Alert")
        alertBuilder.setMessage("Have you completed the payment ?")
        alertBuilder.setCancelable(false)

        alertBuilder.setPositiveButton("Yes") { _, _ ->
            saveBookingCompletedDetails()
        }
        alertBuilder.setNegativeButton("No") { _, _ ->
            //Still Display Payment Activity
        }

    }

    private fun saveBookingCompletedDetails() {
        completeProgressDialog.show()

        val currentUser = auth.currentUser
        val completedData = hashMapOf(
            "completed" to 1,
            "Total Price" to price,
        )

        db.collection("bookingAndroid").document(bid).update(completedData as Map<String, Any>)
            .addOnSuccessListener {
                completeProgressDialog.dismiss()
                startActivity(Intent(this@PaymentActivity, MainActivity::class.java))
                finish()
            }
            .addOnFailureListener {
                completeProgressDialog.dismiss()
                Toast.makeText(applicationContext, "Please try again", Toast.LENGTH_SHORT).show()
            }

    }

    override fun onBackPressed() {
        alertBuilder.show()

    }

    private fun backFuncPaymentActivity() {
        alertBuilder.show()
    }

    private fun loadRecyclerViewItems() {
        progressDialog.show()

        val currentUser = auth.currentUser

        db.collection("Ongoing").document(currentUser?.phoneNumber.toString()).get()
            .addOnSuccessListener {
                bid = it.get("Booking Id") as String
                var serviceList : MutableList<String>
                db.collection("bookingAndroid").document(bid).get()
                    .addOnSuccessListener { bookingSnap ->
                        serviceList = bookingSnap.get("services") as MutableList<String>
                        for (element in serviceList) {
                            val newService = ServiceInfoForPayment(element)
                            sService?.add(newService)
                        }
                        serviceAdapter?.notifyDataSetChanged()

                        val duration = bookingSnap.get("Booking Duration") as Long?
                        val minutes = duration?.div(60L)
                        if (minutes != null) {
                            price = if (minutes >= 60) {
                                179 + (minutes-60)*3
                            } else {
                                179
                            }
                        }
                        amountStat.text = "Rs. $price"

                        progressDialog.dismiss()
                    }
                    .addOnFailureListener {
                        progressDialog.dismiss()
                        Toast.makeText(applicationContext, "Error loading details", Toast.LENGTH_SHORT).show()
                    }
            }
            .addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "Error loading Details", Toast.LENGTH_SHORT).show()
            }

    }
}