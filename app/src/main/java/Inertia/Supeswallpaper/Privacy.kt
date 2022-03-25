package Inertia.Supeswallpaper

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.widget.Toolbar

class Privacy : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val back: Drawable = resources.getDrawable(R.drawable.ic_baseline_arrow_back_ios_24)



        toolbar.setNavigationIcon(back)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        supportActionBar?.hide()
        var wv:WebView = findViewById(R.id.webview)
        wv.loadUrl("https://sites.google.com/view/supeswallprivacy/home?authuser=2")
    }
}