package com.Inertia.Supeswallpaper

import Inertia.Supeswallpaper.R
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.Inertia.Supeswallpaper.Activi.Navigation
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val sp: SharedPreferences = getSharedPreferences("switch", MODE_PRIVATE)
        var onoff = sp.getBoolean("on",false)

        if (onoff){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
        val intent = Intent(this@MainActivity, Navigation::class.java)
        startActivity(intent)
        finish()
        finish()

    }
}