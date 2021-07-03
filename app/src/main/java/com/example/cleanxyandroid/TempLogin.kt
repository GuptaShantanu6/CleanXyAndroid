package com.example.cleanxyandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.google.android.material.bottomsheet.BottomSheetDialog

class TempLogin : AppCompatActivity() {

    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var bottomSheetView : View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temp_login)

        bottomSheetDialog = BottomSheetDialog(
            this@TempLogin, R.style.BottomSheetDialogTheme
        )

        bottomSheetView = LayoutInflater.from(applicationContext).inflate(
            R.layout.bottom_sheet_splash,
            findViewById<LinearLayout>(R.id.BottomSheetSplash)
        )

        bottomSheetDialog.setContentView(bottomSheetView)

        val getStartedButton : View = findViewById(R.id.getStartedView)
        getStartedButton.setOnClickListener {
            bottomSheetDialog.setCanceledOnTouchOutside(true)
            bottomSheetDialog.show()
        }

    }
}