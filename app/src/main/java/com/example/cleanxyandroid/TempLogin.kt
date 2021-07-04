package com.example.cleanxyandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.ViewPager
import com.example.cleanxyandroid.adapter.CredSplashAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout

class TempLogin : AppCompatActivity() {

    private lateinit var tabSplash : TabLayout
    private lateinit var tabSplashViewPager : ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temp_login)

        supportActionBar?.hide()
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.appBlue)

        tabSplash = findViewById(R.id.tabSplash)

        tabSplash.addTab(tabSplash.newTab().setText("Log In"))
        tabSplash.addTab(tabSplash.newTab().setText("Sign Up"))

        tabSplash.tabGravity = TabLayout.GRAVITY_FILL

        tabSplashViewPager = findViewById(R.id.tabSplashViewPager)

        val adapter = CredSplashAdapter(this@TempLogin, supportFragmentManager, tabSplash.tabCount)
        tabSplashViewPager.adapter = adapter

        tabSplashViewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabSplash))
        tabSplash.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    tabSplashViewPager.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

    }
}