package com.example.catalog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.catalog.details.fragments.albums_adapter
import com.example.catalog.details.fragments.gallery_adapter

class detail_gallery : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bandlist =
            ViewModelProvider(requireActivity()).get(AppViewModel::class.java).bandList.value
        val id = (activity as DetailsActivity).clickedElementId
        if (bandlist != null) {
            val band = bandlist.find { it.id == id }
            if (band != null) {

                viewManager = GridLayoutManager(this.context, 3)
                viewAdapter = gallery_adapter(band.images)
                recyclerView = view.findViewById<RecyclerView>(R.id.gallery).apply {
                    setHasFixedSize(true)
                    layoutManager = viewManager
                    adapter = viewAdapter

                }

            }
        }

    }
}