package com.example.cleanxyandroid.bottomNavFragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.cleanxyandroid.ProfileDetailsViewActivity
import com.example.cleanxyandroid.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val profileInfoBtn : ImageView = view.findViewById(R.id.profileInfoBtnProfileFragment)
        profileInfoBtn.setOnClickListener {
            startActivity(Intent(requireContext(), ProfileDetailsViewActivity::class.java))
        }

        val logoutBtn : TextView = view.findViewById(R.id.logOutViewProfileFragment)
        logoutBtn.setOnClickListener {
            Firebase.auth.signOut()
            activity?.finish()
        }

        return view
    }

}