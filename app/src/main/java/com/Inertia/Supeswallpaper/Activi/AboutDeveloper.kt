package com.Inertia.Supeswallpaper.Activi

import Inertia.Supeswallpaper.R
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class AboutDeveloper : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_developer)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val back: Drawable = resources.getDrawable(R.drawable.ic_baseline_arrow_back_ios_24)



        toolbar.setNavigationIcon(back)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        supportActionBar?.hide()
        val insta:TextView = findViewById(R.id.instal)
        val git:TextView = findViewById(R.id.gitl)
        val link:TextView = findViewById(R.id.linkedl)

        insta.movementMethod = LinkMovementMethod.getInstance()
        git.movementMethod = LinkMovementMethod.getInstance()
        link.movementMethod = LinkMovementMethod.getInstance()


    }
}