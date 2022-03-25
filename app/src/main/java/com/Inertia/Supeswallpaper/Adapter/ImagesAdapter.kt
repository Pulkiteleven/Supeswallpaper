package com.Inertia.Supeswallpaper.Adapter

import Inertia.Supeswallpaper.R
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.Inertia.Supeswallpaper.Activi.Wallaper
import com.Inertia.Supeswallpaper.Models.images
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ImagesAdapter(private val mctx:Context,private val imageslist:ArrayList<images>): RecyclerView.Adapter<ImagesAdapter.ImageViewHolder>() {
    private val mCtx:Context = mctx
    private val imagesList:List<images> = imageslist

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImagesAdapter.ImageViewHolder {
        val view = LayoutInflater.from(mCtx).inflate(R.layout.rec_img,parent,false)
        return ImageViewHolder(view,imagesList)
    }


    override fun onBindViewHolder(holder: ImagesAdapter.ImageViewHolder, position: Int) {
        val i = imagesList.get(position)

        val requestOptions = RequestOptions()
            .dontTransform()
            .placeholder(R.drawable.load)

        Glide.with(mCtx)
            .load(i.small)
            .apply(requestOptions)
            .into(holder.imageview!!)
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }

    class ImageViewHolder(v:View,private val imagelist:List<images>):RecyclerView.ViewHolder(v),View.OnClickListener {
       var imageview:ImageView? = null

        init {
            imageview = v.findViewById(R.id.image_view)
            v.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val a = absoluteAdapterPosition
            val i:images = imagelist.get(a)

           val intent = Intent(p0?.context,Wallaper::class.java)
            intent.putExtra("image",i.url)
            intent.putExtra("small",i.small)

            p0?.context?.startActivity(intent)

        }


    }
}