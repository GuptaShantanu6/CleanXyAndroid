@file:Suppress("DEPRECATION")

package com.example.cleanxyandroid

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast

@Suppress("DEPRECATION")
class SplashActivity : AppCompatActivity() {

    private var isConnected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.appBlue)

        val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val introIcon : ImageView = findViewById(R.id.introIcon)

        introIcon.startAnimation(fadeInAnimation)
        fadeInAnimation.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(animation: Animation?) {
                //Not required in this case.
            }

            override fun onAnimationEnd(animation: Animation?) {
                //To only check the internet connectivity after the splash icon animation is completed.

                if (internetConnected()) {
                    startActivity(Intent(this@SplashActivity, TempLogin::class.java))
                }
                else {
                    Toast.makeText(this@SplashActivity,
                        "Internet is not Connected, Please check and restart the connection",
                        Toast.LENGTH_LONG)
                    .show()
                }

            }

            override fun onAnimationRepeat(animation: Animation?) {
                //Not required in this case.
            }

        })

    }

    private fun internetConnected(): Boolean {
        val cm = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork : NetworkInfo? = cm.activeNetworkInfo
        isConnected = activeNetwork?.isConnectedOrConnecting == true

        return isConnected
    }
}