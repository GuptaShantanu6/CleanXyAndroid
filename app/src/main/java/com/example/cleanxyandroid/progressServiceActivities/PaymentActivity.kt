package com.example.cleanxyandroid.progressServiceActivities

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    private lateinit var progressDialog : ProgressDialog

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

        loadRecyclerViewItems()

    }

    private fun loadRecyclerViewItems() {
        progressDialog.show()

        val currentUser = auth.currentUser
        var bid = ""

        db.collection("Ongoing").document(currentUser?.phoneNumber.toString()).get()
            .addOnSuccessListener {
                bid = it.get("Booking Id") as String
                var serviceList : MutableList<*>
                db.collection("bookingAndroid").document(bid).get()
                    .addOnSuccessListener { bookingSnap ->
                        serviceList = bookingSnap.get("services") as MutableList<*>
                        for (element in serviceList) {
                            val newService = ServiceInfoForPayment(element.toString())
                            sService?.add(newService)
                        }
                        serviceAdapter?.notifyDataSetChanged()

                        val duration = it.get("Booking Duration") as Long
                        val hours = duration / 60
                        price = if (hours < 1) {
                            (serviceList.size * 179).toLong()
                        } else {
                            val minutes = duration % 60
                            (hours*179 + minutes*3)
                        }
                        amountStat.text = price.toString()

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