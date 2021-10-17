package com.example.cleanxyandroid.bottomNavFragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.cleanxyandroid.MaidFeedbackActivity
import com.example.cleanxyandroid.R

class HistoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_history, container, false)

//        val tempBtn : Button = view.findViewById(R.id.tempButtonHistoryFragment)
//        tempBtn.setOnClickListener {
//            startActivity(Intent(requireContext(), MaidFeedbackActivity::class.java))
//        }

        return view

    }

}