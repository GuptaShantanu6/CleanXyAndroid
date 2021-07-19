package com.example.cleanxyandroid

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Layout
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroCustomLayoutFragment

class AppIntroActivity :  AppIntro() {
    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        supportActionBar?.hide()

        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.app_intro_1))
        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.app_intro_2))
        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.app_intro_3))

    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        startActivity(Intent(this, TempLogin::class.java))
        finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        startActivity(Intent(this,TempLogin::class.java))
        finish()
    }
}