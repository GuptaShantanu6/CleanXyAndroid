package com.example.cleanxyandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView

@Suppress("DEPRECATION")
class ProfileDetailsViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_details_view)

        supportActionBar?.hide()
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.appBlue)

        val editBtn : TextView = findViewById(R.id.profileEditBtnProfileDetailsActivity)
        editBtn.setOnClickListener {
            startActivity(Intent(this, ProfileNewDetailsActivity::class.java))
        }

        val backBtn : ImageView = findViewById(R.id.backBtnProfileDetailsViewActivity)
        backBtn.setOnClickListener {
            onBackPressed()
        }

    }
}