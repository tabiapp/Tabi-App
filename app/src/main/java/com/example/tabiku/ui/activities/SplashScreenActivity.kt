package com.example.tabiku.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.tabiku.R

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Delay selama 3 detik sebelum berpindah ke MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
            // Intent untuk berpindah ke MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Menutup SplashScreenActivity agar tidak kembali ke layar ini
        }, 3000) // 3000 ms = 3 detik
    }
}