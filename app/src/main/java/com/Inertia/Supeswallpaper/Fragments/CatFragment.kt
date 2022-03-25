package com.Inertia.Supeswallpaper.Fragments

import Inertia.Supeswallpaper.R
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.Inertia.Supeswallpaper.Adapter.CategoryAdapter
import com.Inertia.Supeswallpaper.Models.Category
import com.google.firebase.database.*


import kotlin.math.log

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CatFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CatFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var progressBar: ProgressBar? = null
    private var categorylist: MutableList<Category>? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: CategoryAdapter? = null

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
        return inflater.inflate(Inertia.Supeswallpaper.R.layout.fragment_cat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = view.findViewById<ProgressBar>(Inertia.Supeswallpaper.R.id.progressbar)

            progressBar?.visibility = View.VISIBLE

            recyclerView = view.findViewById(Inertia.Supeswallpaper.R.id.recyclerview) as RecyclerView
            recyclerView!!.layoutManager = GridLayoutManager(activity, 1)

            categorylist = ArrayList()
            //categorylist?.add(a)
            //categorylist?.add(b)
            adapter = CategoryAdapter(activity as Context, categorylist as ArrayList<Category>)
            recyclerView!!.adapter = adapter

            val ref = FirebaseDatabase.getInstance().getReference("CategoryBakground")
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.exists()) {
                        progressBar?.visibility = View.GONE
                        for (ds: DataSnapshot in p0.children) {
                            val naam = ds.child("name").value
                            val imagelink = ds.child("imageLink").value
                            val c: Category = Category(naam.toString(), imagelink.toString())
                            categorylist?.add(c)
                        }
                        adapter?.notifyDataSetChanged()
                    } else {
                        print("NO IT DOSENT")
                    }
                }

                override fun onCancelled(p0: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CatFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CatFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}