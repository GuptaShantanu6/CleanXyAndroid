@file:Suppress("DEPRECATION")

package com.example.cleanxyandroid.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.cleanxyandroid.TabSplashFragments.LogInFragment
import com.example.cleanxyandroid.TabSplashFragments.RegisterFragment

internal class CredSplashAdapter (
    var context : Context,
    fm : FragmentManager,
    private var totalTabs : Int
) :
        FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return totalTabs
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                LogInFragment()
            }
            1 -> {
                RegisterFragment()
            }
            else ->getItem(position)
        }
    }

}