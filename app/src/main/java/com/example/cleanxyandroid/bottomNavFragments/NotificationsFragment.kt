package com.example.cleanxyandroid.bottomNavFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.cleanxyandroid.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class NotificationsFragment : Fragment() {

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

        return view
    }

}