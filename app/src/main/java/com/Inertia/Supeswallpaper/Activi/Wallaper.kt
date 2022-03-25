package com.Inertia.Supeswallpaper.Activi

import Inertia.Supeswallpaper.R
import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.ImageView
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.Inertia.Supeswallpaper.Models.images
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.github.clans.fab.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.lang.Exception
import java.lang.reflect.Type
import java.util.jar.Manifest

class Wallaper : AppCompatActivity() {
    public lateinit var favList:MutableList<images>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallaper)
        val sp:SharedPreferences = getSharedPreferences("switch", MODE_PRIVATE)
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

        val url: String? = intent.getStringExtra("image")
        val small: String? = intent.getStringExtra("small")
        favList = ArrayList()


        val image:ImageView = findViewById(R.id.wallaper)

        Glide.with(this)
            .load(url)
            .into(image)

        val set:FloatingActionButton = findViewById(R.id.setwall)
        val save:FloatingActionButton = findViewById(R.id.savewall)
        val fav:FloatingActionButton = findViewById(R.id.favourite)

        set.setOnClickListener {
            //Toast.makeText(this, "Set", Toast.LENGTH_SHORT).show()
           saver(url.toString(),false)
        }
        save.setOnClickListener{
            //Toast.makeText(this, "Save", Toast.LENGTH_SHORT).show()
            saver(url.toString(),true)
        }
        fav.setOnClickListener {
            //Toast.makeText(this, "Fav", Toast.LENGTH_SHORT).show()




            val sharedPreferences:SharedPreferences = getSharedPreferences("fav", MODE_PRIVATE)
            val edit:SharedPreferences.Editor = sharedPreferences.edit()


            val gsoon:Gson = Gson()

            val jsoon:String ?= sharedPreferences.getString("favs",null)
            if (jsoon!=null) {
                val type: Type = object : TypeToken<MutableList<images>>() {}.type
                favList = gsoon.fromJson(jsoon, type)
            }
            var tf:Boolean = true
                val i: images = images(url.toString(), small.toString())
                for (x in 0..favList.size-1){
                    if (favList[x].small.equals(small)){
                        tf = false
                        break
                    }
                }
               if (tf) {
                   favList.add(i)
                   Toast.makeText(this, "Wallpaper Added to Favourites", Toast.LENGTH_SHORT).show()
               }
            else{
                   Toast.makeText(this, "Wallpaper Already in Favourites", Toast.LENGTH_SHORT).show()
            }
                val gson: Gson = Gson()
                val json: String = gson.toJson(favList)
                edit.putString("favs", json)
                edit.apply()


        }



    }


    fun saver(url: String,yn:Boolean){
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),100)
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),100)
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object :SimpleTarget<Bitmap>(){
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    downloadImage(resource,url.toString(),yn)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    TODO("Not yet implemented")
                }
            })
    }
    fun downloadImage(image:Bitmap,url: String,yn:Boolean){
        var savedPath: String? = null
        val imageFileName: String = url.substring(url.length - 10).toString() + ".jpg"
        val storage: File = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"SupesWallpaper")

       if (!storage.exists()) {
            storage.mkdir()
        }
        if(storage.exists()) {
            val imageFile = File(storage, imageFileName)
            savedPath = imageFile.absolutePath
            try {
                val fOut: OutputStream = FileOutputStream(imageFile)
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
                fOut.close()
                if (yn){
                Toast.makeText(this, "Wallaper Saved", Toast.LENGTH_SHORT).show()}
            } catch (e: Exception) {
                e.printStackTrace()
            }
            addpic(savedPath)
            if(!yn){
                setWallaper(Uri.parse(savedPath))
            }
        }
    }
    fun addpic(imagepath:String){
            val media = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            val f = File(imagepath)
            val content = Uri.fromFile(f)
            media.setData(content)
            this.sendBroadcast(media)
        }

    fun setWallaper(uri:Uri){
        val intent = Intent(Intent.ACTION_ATTACH_DATA)
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.setDataAndType(uri,"image/jpeg")
        intent.putExtra("mimeType","image/jpeg")
        startActivity(Intent.createChooser(intent,"Set as:"))
    }


}


