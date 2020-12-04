package com.example.catalog

import android.app.Application
import android.content.res.Resources
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val DRAGDIRECTIONS = 0
private const val DEFAULTCATEGORYPOSITION = -1

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {


    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var appViewModel: AppViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appViewModel = ViewModelProvider(this).get(AppViewModel::class.java)
        appViewModel.loadCategories(resources.getStringArray(R.array.filters))
        viewManager = LinearLayoutManager(this)
        viewAdapter = ListAdapter(appViewModel.bandList.value!!, appViewModel::onFavouriteClick)

        val itemTouchHelper = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(DRAGDIRECTIONS, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false //Not implementing moving elments for this app (yet?)
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    appViewModel.onSwipe(viewHolder.layoutPosition)
                }
            })

        recyclerView = findViewById<RecyclerView>(R.id.list).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter

        }
        itemTouchHelper.attachToRecyclerView(recyclerView)
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
        appViewModel.onCategorySelected(DEFAULTCATEGORYPOSITION)
    }



    }