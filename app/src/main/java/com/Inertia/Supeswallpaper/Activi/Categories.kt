package com.Inertia.Supeswallpaper.Activi

import Inertia.Supeswallpaper.R
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.Inertia.Supeswallpaper.Adapter.ImagesAdapter
import com.Inertia.Supeswallpaper.Models.images
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Categories : AppCompatActivity() {
    private var progressBar: ProgressBar? = null
    private var imagesList:MutableList<images>? = null
    private var recyclerView: RecyclerView?= null
    private var adapter: ImagesAdapter?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        val sp: SharedPreferences = getSharedPreferences("switch", MODE_PRIVATE)
        var a = sp.getBoolean("on",false)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val back: Drawable = resources.getDrawable(R.drawable.ic_baseline_arrow_back_ios_24)
        if (!a){
            back.setTint(resources.getColor(R.color.text))
        }
        else{
            back.setTint(resources.getColor(R.color.textd))

        }

        toolbar.setNavigationIcon(back)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        supportActionBar?.hide()
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val textView = findViewById<TextView>(R.id.toolbar_title)

        val intent = intent
        val cat: String? = intent.
        getStringExtra("category")
        textView.setText(cat)

        progressBar = findViewById(R.id.progressbar) as ProgressBar
        progressBar?.visibility = View.VISIBLE

        recyclerView = findViewById(R.id.recyclerview) as RecyclerView
        recyclerView!!.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

        imagesList = ArrayList()

        adapter = ImagesAdapter(this, imagesList as ArrayList<images>)
        recyclerView!!.adapter = adapter

        val ref = FirebaseDatabase.getInstance().getReference("Wallpaper").child(cat.toString())
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    progressBar?.visibility = View.GONE
                    for (ds: DataSnapshot in snapshot.children){
                        val url = ds.child("url").value
                        val small = ds.child("small").value
                        val i:images = images(url.toString(), small.toString())
                        imagesList?.add(i)
                    }
                    adapter?.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })




    }
}