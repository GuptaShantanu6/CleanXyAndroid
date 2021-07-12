package com.example.cleanxyandroid.bottomNavFragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cleanxyandroid.R
import com.example.cleanxyandroid.TempLogin
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

//        val logOutBtn : Button = view.findViewById(R.id.logOutBtnProfile)
//        logOutBtn.setOnClickListener {
//            Firebase.auth.signOut()
//            startActivity(Intent(activity, TempLogin::class.java))
//            activity?.finish()
//        }

        return view
    }

}