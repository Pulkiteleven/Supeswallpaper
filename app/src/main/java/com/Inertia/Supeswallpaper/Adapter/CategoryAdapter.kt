package com.Inertia.Supeswallpaper.Adapter

import Inertia.Supeswallpaper.R
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.Inertia.Supeswallpaper.Activi.Categories
import com.Inertia.Supeswallpaper.Models.Category
import com.bumptech.glide.Glide

class CategoryAdapter(private val mctx:Context,private val categorylist:ArrayList<Category>) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    private val mCtx:Context = mctx
    private val categoryList:List<Category> = categorylist



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {
        val view = LayoutInflater.from(mCtx).inflate(R.layout.rec_cat,parent,false)
        return CategoryViewHolder(view,categoryList)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
    val c = categoryList.get(position)
        holder.textview?.setText(c.name)

            Glide.with(mCtx)
                .load(c.imaelink)
                .into(holder.imageview!!)

    }

    override fun getItemCount(): Int {
        return categorylist.size

    }

    class CategoryViewHolder(v:View,private val categorylist:List<Category>):RecyclerView.ViewHolder(v),View.OnClickListener{
        var textview:TextView? = null
        var imageview:ImageView? = null


        init {

            textview = v.findViewById(R.id.text_view_cat_name)
            imageview = v.findViewById(R.id.image_view)
            v.setOnClickListener(this)

        }

        override fun onClick(p0: View?) {
            val i = absoluteAdapterPosition
            val c:Category = categorylist.get(i)

            val intent = Intent(p0?.context,Categories::class.java)
            intent.putExtra("category",c.name)
            p0?.context?.startActivity(intent)
        }


    }


}

