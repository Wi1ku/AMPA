package com.example.catalog

import android.app.Application
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    companion object {
        lateinit var context: Application
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var appViewModel: AppViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = application
        setContentView(R.layout.activity_main)
        appViewModel = ViewModelProvider(this).get(AppViewModel::class.java)
        viewManager = LinearLayoutManager(this)
        viewAdapter = ListAdapter(appViewModel.bandList.value!!, appViewModel::onFavouriteClick)

        recyclerView = findViewById<RecyclerView>(R.id.list).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter

        }

        appViewModel.bandList.observe(this, Observer { recyclerView.apply { adapter = ListAdapter(appViewModel.getFilteredBands(), appViewModel::onFavouriteClick) } })
        appViewModel.selectedCat.observe(this, Observer { recyclerView.apply { adapter = ListAdapter(appViewModel.getFilteredBands(), appViewModel::onFavouriteClick) } })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val menuItem = menu!!.findItem(R.id.cat_filter)
        val spinner = menuItem.actionView as Spinner
        ArrayAdapter.createFromResource(
            this,
            R.array.filters,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = this
        spinner.setSelection(appViewModel.getSelectedCatIndex())
        return true

    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent != null) {
             appViewModel.onCategorySelected(position)
        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        appViewModel.onCategorySelected(-1)
    }




}