package com.Inertia.Supeswallpaper.Fragments

import Inertia.Supeswallpaper.R
import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.Inertia.Supeswallpaper.Adapter.ImagesAdapter
import com.Inertia.Supeswallpaper.Models.images
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LatestFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavFragment: Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var progressBar:ProgressBar? = null
    private var imagesList:MutableList<images>? = null
    private var iL:MutableList<images>? = null

    private var recyclerView:RecyclerView?= null
    private var adapter:ImagesAdapter?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = view.findViewById(R.id.progressbar) as ProgressBar
        progressBar?.visibility = View.VISIBLE

        recyclerView = view.findViewById(R.id.recyclerview) as RecyclerView
        recyclerView!!.layoutManager = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)

        imagesList = ArrayList()
        iL = ArrayList()

        adapter = ImagesAdapter(activity as Context, iL as ArrayList<images>)
        recyclerView!!.adapter = adapter


        val sharedPreferences:SharedPreferences = requireContext().getSharedPreferences("fav", MODE_PRIVATE)

        val gson:Gson = Gson()

        val json:String ?= sharedPreferences.getString("favs",null)
        if (json!=null) {
            val type: Type = object : TypeToken<MutableList<images>>() {}.type
            imagesList = gson.fromJson(json, type)
        }
        else{
            val txt:TextView = view.findViewById(R.id.txtt)
            txt.visibility = View.VISIBLE
        }
        for(x in 0..imagesList!!.size-1) {
            val i: images = images(
                (imagesList as ArrayList<images>)[x].url,
                (imagesList as ArrayList<images>)[x].small
            )
            iL?.add(i)
        }
        if (iL != null){
            progressBar?.visibility = View.GONE
        }
        //val i:images = images("https://imagizer.imageshack.com/img922/1531/dcKgOu.jpg", "https://i.postimg.cc/05Sq60hj/Ant-Man-Minimal-i-Phone-Wallpaper-1-FILEminimizer.jpg")
        //imagesList?.add(i)
        adapter?.notifyDataSetChanged()





    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LatestFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LatestFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}