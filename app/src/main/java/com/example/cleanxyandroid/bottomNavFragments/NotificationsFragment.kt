package com.example.cleanxyandroid.bottomNavFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanxyandroid.R
import com.example.cleanxyandroid.notificationRecylerAdapter
import com.example.cleanxyandroid.notification_data
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class NotificationsFragment : Fragment() {

    private var notificationData: ArrayList<notification_data>? = null
    private var notificationRecycler: RecyclerView? = null
    private var tv_notification_count: TextView? =null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_notifications, container, false)

        val backBtn : ImageView = view.findViewById(R.id.backBtnNotificationsFragment)
        backBtn.setOnClickListener {
            val botNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavBar)
            botNav.selectedItemId = R.id.homeIcon
        }

        notificationData = java.util.ArrayList()
        notificationRecycler = view.findViewById<RecyclerView>(R.id.notificationRecycler)

        tv_notification_count=view.findViewById(R.id.notification_count)

        setUserInfo()
        setAdapter()

        return view
    }

    private fun setAdapter() {
        val adapter = notificationRecylerAdapter(notificationData)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)

        tv_notification_count?.setText(notificationData?.size.toString())

        notificationRecycler?.setLayoutManager(layoutManager)
        notificationRecycler?.setItemAnimator(DefaultItemAnimator())
        notificationRecycler?.setAdapter(adapter)


    }

    private fun setUserInfo() {

        notificationData?.add(
            notification_data(
                "Your slot with Niranjana has been confirmed ",
                "16:27"
            )
        )
        notificationData?.add(
            notification_data(
                "Today’s special offer :  Laundry work 25 % off.  Valid till 20th May ",
                "16:27"
            )
        )
    }

}