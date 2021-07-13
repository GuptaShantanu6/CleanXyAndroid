package com.example.cleanxyandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

@Suppress("DEPRECATION")
class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val goToLogIn : TextView = findViewById(R.id.signup_already_has_acc)
        goToLogIn.setOnClickListener {
            startActivity(Intent(this@Register, Login::class.java))
        }

    }
}