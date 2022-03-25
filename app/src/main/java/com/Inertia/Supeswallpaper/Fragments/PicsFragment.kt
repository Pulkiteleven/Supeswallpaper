package com.Inertia.Supeswallpaper.Fragments

import Inertia.Supeswallpaper.R
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.Inertia.Supeswallpaper.Adapter.ImagesAdapter
import com.Inertia.Supeswallpaper.Models.images
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PicsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PicsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var progressBar: ProgressBar? = null
    private var imagesList:MutableList<images>? = null
    private var recyclerView: RecyclerView?= null
    private var adapter: ImagesAdapter?= null

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
        return inflater.inflate(R.layout.fragment_pics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = view.findViewById(R.id.progressbar) as ProgressBar
        progressBar?.visibility = View.VISIBLE

        recyclerView = view.findViewById(R.id.recyclerview) as RecyclerView
        recyclerView!!.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

        imagesList = ArrayList()

        adapter = ImagesAdapter(activity as Context, imagesList as ArrayList<images>)
        recyclerView!!.adapter = adapter

        val ref = FirebaseDatabase.getInstance().getReference("Custom Categories").child("Pics")
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PicsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PicsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}