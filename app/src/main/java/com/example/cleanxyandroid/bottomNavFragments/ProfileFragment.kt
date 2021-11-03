package com.example.cleanxyandroid.bottomNavFragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.cleanxyandroid.profileActivities.ProfileDetailsViewActivity
import com.example.cleanxyandroid.R
import com.example.cleanxyandroid.TempLogin
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {

    private lateinit var nameText : TextView
    private lateinit var phoneText : TextView

    private lateinit var auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore

    private lateinit var progressDialog : ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        nameText = view.findViewById(R.id.nameProfileFragment)
        phoneText = view.findViewById(R.id.phoneNumberProfileFragment)

        auth = Firebase.auth
        db = Firebase.firestore

        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Loading Details...")
        progressDialog.setMessage("Please Wait")
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)

        loadNameAndPhone()

        val profileInfoBtn : ImageView = view.findViewById(R.id.profileInfoBtnProfileFragment)
        profileInfoBtn.setOnClickListener {
            startActivity(Intent(requireContext(), ProfileDetailsViewActivity::class.java))
        }

        val logoutBtn : TextView = view.findViewById(R.id.logOutViewProfileFragment)
        logoutBtn.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(requireActivity(), TempLogin::class.java))
            activity?.finish()
        }

        val historyIcon : ImageView = view.findViewById(R.id.historyIconProfileFragment)
        val historyText : TextView = view.findViewById(R.id.historyTextHistoryFragment)

        historyIcon.setOnClickListener {
            goToHistoryTab()
        }

        historyText.setOnClickListener {
            goToHistoryTab()
        }

        return view
    }

    private fun loadNameAndPhone() {
        val currentUser = auth.currentUser
        progressDialog.show()

        db.collection("customerAndroid").document(currentUser?.phoneNumber.toString()).get()
            .addOnSuccessListener {
                nameText.text = it.get("name") as String?
                phoneText.text = it.get("phoneNumber") as String?
                progressDialog.dismiss()
            }
            .addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(context, "Error retrieving details", Toast.LENGTH_SHORT).show()
            }
    }

    private fun goToHistoryTab() {
        val botNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavBar)
        botNav.selectedItemId = R.id.historyIcon
    }

}