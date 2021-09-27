@file:Suppress("DEPRECATION")

package com.example.cleanxyandroid

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanxyandroid.adapters.ServiceBookingAdapter
import com.example.cleanxyandroid.adapters.addressBookingAdapter
import com.example.cleanxyandroid.confirmOrFailActivities.BookingSuccessfulActivity
import com.example.cleanxyandroid.model.addressInfoForBooking
import com.example.cleanxyandroid.model.serviceInfoForBooking
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class BookingActivity : AppCompatActivity() {

    private var serviceRecyclerView : RecyclerView? = null
    private var serviceAdapter : ServiceBookingAdapter? = null
    private var sService : MutableList<serviceInfoForBooking>? = null

    private var addressRecyclerView : RecyclerView? = null
    private var addressItemAdapter :addressBookingAdapter? = null
    private var aAddressItem :MutableList<addressInfoForBooking>? = null

    private lateinit var db : FirebaseFirestore
    private lateinit var progressDialog : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        supportActionBar?.hide()
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.appBlue)

        db = FirebaseFirestore.getInstance()

        val servicesList : Array<Int> = intent.getSerializableExtra("ss") as Array<Int>
        val fullTime : Array<Int> = intent.getSerializableExtra("fullTime") as Array<Int>

        for (element in fullTime) {
            Log.d("fullTime", element.toString())
        }

        var count = 0
        for (element in servicesList) {
            count += element
        }

        val priceText : TextView = findViewById(R.id.priceTextBookingActivity)
        priceText.text = "Rs. " + (count*150).toString()

        serviceRecyclerView = findViewById(R.id.servicesRecyclerViewBookingActivity)
        serviceRecyclerView?.setHasFixedSize(true)

        val linearLayoutManager = LinearLayoutManager(baseContext)
        serviceRecyclerView!!.layoutManager = linearLayoutManager

        sService = ArrayList()
        serviceAdapter = baseContext?.let { ServiceBookingAdapter(it, false, sService as ArrayList<serviceInfoForBooking>) }
        serviceRecyclerView?.adapter = serviceAdapter

        for (i in servicesList.indices) {
            if (servicesList[i] == 1) {
                when (i) {
                    0 -> {
                        val newService = serviceInfoForBooking(i+1, "Floor Cleaning", "150")
                        (sService as ArrayList<serviceInfoForBooking>).add(newService)
                    }
                    1 -> {
                        val newService = serviceInfoForBooking(i+1, "Washing Utensils", "150")
                        (sService as ArrayList<serviceInfoForBooking>).add(newService)
                    }
                    2 -> {
                        val newService = serviceInfoForBooking(i+1, "Cleaning Kitchen", "150")
                        (sService as ArrayList<serviceInfoForBooking>).add(newService)
                    }
                    3 -> {
                        val newService = serviceInfoForBooking(i+1, "Washing", "150")
                        (sService as ArrayList<serviceInfoForBooking>).add(newService)
                    }
                    4 -> {
                        val newService = serviceInfoForBooking(i+1, "Cleaning Bathroon", "150")
                        (sService as ArrayList<serviceInfoForBooking>).add(newService)
                    }
                    5 -> {
                        val newService = serviceInfoForBooking(i+1, "GroceryShopping", "150")
                        (sService as ArrayList<serviceInfoForBooking>).add(newService)
                    }
                    else -> {
                        val newService = serviceInfoForBooking(i+1, "Cleaning", "150")
                        (sService as ArrayList<serviceInfoForBooking>).add(newService)
                    }
                }
            }
        }

        serviceAdapter?.notifyDataSetChanged()

        val addressText : TextView = findViewById(R.id.addressTextBookingActivity)

        addressRecyclerView = findViewById(R.id.addressRecyclerViewBookingActivity)
        addressRecyclerView?.setHasFixedSize(true)

        val addressLinearLayoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.HORIZONTAL, false)
        addressRecyclerView!!.layoutManager = addressLinearLayoutManager

        aAddressItem = ArrayList()
        addressItemAdapter = baseContext?.let { addressBookingAdapter(it, false, aAddressItem as ArrayList<addressInfoForBooking>) }

        addressRecyclerView!!.adapter = addressItemAdapter

        val newAddress = addressInfoForBooking("hello world")
        (aAddressItem as ArrayList<addressInfoForBooking>).add(newAddress)
        (aAddressItem as ArrayList<addressInfoForBooking>).add(addressInfoForBooking("123"))
        (aAddressItem as ArrayList<addressInfoForBooking>).add(addressInfoForBooking("456"))

        addressItemAdapter?.notifyDataSetChanged()

        addressText.text = (aAddressItem as ArrayList<addressInfoForBooking>)[0].addressText

        addressItemAdapter?.setOnItemClickListener(object : addressBookingAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                addressText.text = (aAddressItem as ArrayList<addressInfoForBooking>)[position].addressText
            }
        })


        val backBtn : ImageView = findViewById(R.id.backBtnScheduleSlotActivity)
        backBtn.setOnClickListener {
            onBackPressed()
        }

        progressDialog = ProgressDialog(this@BookingActivity)
        progressDialog.setTitle("Booking Order...")
        progressDialog.setMessage("Please Wait")
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)

        val confirmBookingBtn : Button = findViewById(R.id.confirmSlotBtnBookingActivity)
        confirmBookingBtn.setOnClickListener {
            progressDialog.show()

            val bid = getBookingId(20)
            val address = ""
            val st = arrayListOf<String>()
            val services : MutableList<String> = st.toMutableList()
            var avgPerMinCost = 0
            var c = 0
            for (i in 0..6) {
                if (servicesList[i] == 1) {
                    c++
                    avgPerMinCost += i+5
                    when (i) {
                        0 -> {
                            services.add("Floor Cleaning")
                        }
                        1 -> {
                            services.add("Washing Utensils")
                        }
                        2 -> {
                            services.add("Cleaning Kitchen")
                        }
                        3 -> {
                            services.add("Washing")
                        }
                        4 -> {
                            services.add("Cleaning Bathroom")
                        }
                        5 -> {
                            services.add("Grocery Shopping")
                        }
                        else -> {
                            services.add("Cleaning")
                        }
                    }
                }
            }
            avgPerMinCost /= c
            val completed = 0
            val phoneNumber = Firebase.auth.currentUser?.phoneNumber.toString()

            var date = ""
            date += fullTime[3].toString() + "/" + fullTime[4].toString() + "/" + fullTime[5].toString()

            var time = ""
            time += fullTime[0].toString() + ":" + fullTime[1].toString() + " " + fullTime[2].toString()

            saveBookingData(bid, address, avgPerMinCost, completed, date, phoneNumber, services, time)

        }


    }

    private fun saveBookingData(
        bid: String,
        address: String,
        avgPerMinCost: Int,
        completed: Int,
        date: String,
        phoneNumber: String,
        services: MutableList<String>,
        time: String
    ) {
        val bookingData = hashMapOf(
            "address" to address,
            "avgPerMinCost" to avgPerMinCost,
            "completed" to completed,
            "date" to date,
            "phoneNumber" to phoneNumber,
            "services" to services,
            "time" to time
        )

        db.collection("bookingAndroid").document(bid).set(bookingData)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    progressDialog.dismiss()
                    startActivity(Intent(applicationContext, BookingSuccessfulActivity::class.java))
                }
                else {
                    Toast.makeText(applicationContext, "Booking failed, Please try again", Toast.LENGTH_LONG).show()
                }
            }

    }

    private fun getBookingId(length : Int) : String {
        val charset = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz0123456789"
        return (1..length).map { charset.random() }
            .joinToString("")
    }
}