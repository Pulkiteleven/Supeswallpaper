package com.Inertia.Supeswallpaper.Activi

import Inertia.Supeswallpaper.BuildConfig
import Inertia.Supeswallpaper.Privacy
import Inertia.Supeswallpaper.R
import Inertia.Supeswallpaper.databinding.ActivityNavigationBinding
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.ui.AppBarConfiguration
import com.Inertia.Supeswallpaper.Fragments.*
import com.google.android.material.navigation.NavigationView
import nl.joery.animatedbottombar.AnimatedBottomBar

class Navigation : AppCompatActivity() ,NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityNavigationBinding
    private lateinit var drawerLayout: DrawerLayout
    lateinit var toolbart:TextView
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        val sp:SharedPreferences = getSharedPreferences("switch", MODE_PRIVATE)
        var onoff = sp.getBoolean("on",false)


        super.onCreate(savedInstanceState)


        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toolbart = findViewById<TextView>(R.id.toolbar_title)
        setSupportActionBar(binding.appBarNavigation.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        drawerLayout= binding.drawerLayout
        val navView: NavigationView = binding.navView
        val bottombar: AnimatedBottomBar = binding.appBarNavigation.bottomBar
        navView.bringToFront()
        val toggle = ActionBarDrawerToggle(this@Navigation,drawerLayout,binding.appBarNavigation.toolbar,0,0)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
        toggle.setToolbarNavigationClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        DisplayFragment(LatestFragment(),"Latest")
        bottombar.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener{
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {
                when(newIndex){
                    0 -> DisplayFragment(PicsFragment(),"Our Pics")
                    1 -> DisplayFragment(CatFragment(),"Categories")
                    2 -> DisplayFragment(LatestFragment(),"Latest")
                    3 -> DisplayFragment(FeatureFragment(),"Feature")
                    4 -> DisplayFragment(FavFragment(),"Favourite")
                    else -> DisplayFragment(LatestFragment(),"Latest")
                }
            }
        })

        val menuswith = navView.menu.findItem(R.id.nav_dark)
        val switchid = menuswith.actionView as SwitchCompat
        switchid.isChecked = onoff


        switchid.setOnClickListener {
            val sp:SharedPreferences = getSharedPreferences("switch", MODE_PRIVATE)
            val edit:SharedPreferences.Editor = sp.edit()
            switcher(switchid,edit)
            //finish()
        }

    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        else{
            super.onBackPressed()
        }
    }
    //for side waali setting button

    /**override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.navigation, menu)
        return true
    }**/

    public fun DisplayFragment(fragment:Fragment,toolattext:String){
        supportFragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in,android.R.animator.fade_out)
            .replace(R.id.content_main,fragment).commit()
        toolbart.setText(toolattext)


    }

    fun switcher(s:SwitchCompat,edit:SharedPreferences.Editor){
        if(s.isChecked){
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
            edit.putBoolean("on",true)
            edit.apply()

        }
        else{
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
            edit.putBoolean("on",false)
            edit.apply()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_info ->{
                val intent = Intent(this@Navigation,AboutUs::class.java)
                startActivity(intent)
            }
            R.id.nav_privacy ->{
                val intent = Intent(this@Navigation,Privacy::class.java)
                startActivity(intent)
            }
            R.id.nav_dev ->{
                val intent = Intent(this@Navigation,AboutDeveloper::class.java)
                startActivity(intent)
            }
            R.id.nav_share ->{
                shareit()
            }
            R.id.nav_rate ->{
                RateUs()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true

    }

    fun shareit(){
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "SupesWallpaper for Superheroes")
        var shareMessage = "\nLet me recommend you this application\n\n"
        shareMessage =
            """
            ${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}
            
            
            """.trimIndent()
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
        startActivity(Intent.createChooser(sharingIntent, "choose one"))
    }
    private fun RateUs() {
        val uri = Uri.parse("market://details?id=" + applicationContext.packageName)
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(
            Intent.FLAG_ACTIVITY_NO_HISTORY or
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        )
        try {
            startActivity(goToMarket)
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + applicationContext.packageName)
                )
            )
        }
    }


}