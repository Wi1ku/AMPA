package com.example.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider


class detail_main : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bandlist = ViewModelProvider(requireActivity()).get(AppViewModel::class.java).bandList.value
        val id = (activity as DetailsActivity).clickedElementId
        if(bandlist != null){
            val band = bandlist.find { it.id == id}
            if (band != null){
                val text = view.findViewById<TextView>(R.id.desc)
                text.text = band.description
            }
        }


    }

}