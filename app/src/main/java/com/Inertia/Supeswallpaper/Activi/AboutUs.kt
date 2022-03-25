package com.Inertia.Supeswallpaper.Activi

import Inertia.Supeswallpaper.R
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar

class AboutUs : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val back: Drawable = resources.getDrawable(R.drawable.ic_baseline_arrow_back_ios_24)



        toolbar.setNavigationIcon(back)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        supportActionBar?.hide()
    }
}