package com.example.cleanxyandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanxyandroid.adapters.ServiceBookingAdapter
import com.example.cleanxyandroid.model.serviceInfoForBooking

class BookingActivity : AppCompatActivity() {

    private var serviceRecyclerView : RecyclerView? = null
    private var serviceAdapter : ServiceBookingAdapter? = null
    private var sService : MutableList<serviceInfoForBooking>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        supportActionBar?.hide()
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.appBlue)

        val servicesList : Array<Int> = intent.getSerializableExtra("ss") as Array<Int>
        val fullTime : Array<Int> = intent.getSerializableExtra("fullTime") as Array<Int>

        var c = 0
        for (element in servicesList) {
            c += element
        }

        val priceText : TextView = findViewById(R.id.priceTextBookingActivity)
        priceText.text = "Rs. " + (c*150).toString()

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

    }
}