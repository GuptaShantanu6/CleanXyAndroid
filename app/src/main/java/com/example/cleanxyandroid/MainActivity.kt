package com.example.cleanxyandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.cleanxyandroid.bottomNavFragments.HistoryFragment
import com.example.cleanxyandroid.bottomNavFragments.HomeFragment
import com.example.cleanxyandroid.bottomNavFragments.NotificationsFragment
import com.example.cleanxyandroid.bottomNavFragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.appBlue)

        val homeFragment = HomeFragment()
        val notificationsFragment = NotificationsFragment()
        val historyFragment = HistoryFragment()
        val profileFragment = ProfileFragment()
        
        setCurrentFragment(homeFragment)

        val bottomNav : BottomNavigationView = findViewById(R.id.bottomNavBar)

        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.homeIcon -> setCurrentFragment(homeFragment)
                R.id.notificationsIcon -> setCurrentFragment(notificationsFragment)
                R.id.historyIcon -> setCurrentFragment(historyFragment)
                R.id.profileIcon -> setCurrentFragment(profileFragment)
            }
            true
        }

    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameHome, fragment)
            commit()
        }
    }

    override fun onBackPressed() {
        Toast.makeText(this@MainActivity, "Please log out to go back", Toast.LENGTH_SHORT).show()
    }
}