package com.example.tabi.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.tabi.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Hubungkan BottomNavigationView dengan NavController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)

        // Mencari NavHostFragment secara aman
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)

        if (navHostFragment != null) {
            // Dapatkan NavController dan hubungkan dengan BottomNavigationView
            val navController = (navHostFragment as NavHostFragment).navController
            bottomNavigationView.setupWithNavController(navController)
        } else {
            // Tangani kasus di mana NavHostFragment tidak ditemukan
            Log.e("MainActivity", "NavHostFragment tidak ditemukan!")
        }
    }

}