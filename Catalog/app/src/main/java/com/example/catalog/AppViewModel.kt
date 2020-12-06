package com.example.catalog

import android.app.Application
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.ItemTouchHelper

class AppViewModel : ViewModel() {

    var categories: Array<String> = arrayOf()

    private val _selectedCat: MutableLiveData<String> = MutableLiveData("")
    var selectedCat: LiveData<String> = _selectedCat

    private val _bandList: MutableLiveData<MutableList<ListElement>> = MutableLiveData(mutableListOf(
        ListElement(1,"Beatles", "Rock", R.drawable.ic_launcher_background, "beatels", listOf(Album("Abbey Road", "1969", R.drawable.ic_launcher_foreground)), listOf(R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground), false),
        ListElement(2,"Fleetwood Mac", "Rock", R.drawable.ic_launcher_background, "fm", listOf(),listOf(),false),
        ListElement(3,"Jimi Hendrix", "Rock", R.drawable.ic_launcher_background, "JH", listOf(),listOf(),false),
        ListElement(4,"Miles Davis", "Jazz", R.drawable.ic_launcher_foreground, "MD", listOf(),listOf(),false)))
    var bandList: LiveData<MutableList<ListElement>> = _bandList


    fun getFilteredBands(): MutableList<ListElement> {
        return when {
            //TODO: remove hardcoded strings
            (selectedCat.value == "All Categories") -> bandList.value!!
            (selectedCat.value == "Favourites") -> bandList.value!!.filter { band -> band.isFavourite }
                .toMutableList()
            else -> bandList.value!!.filter { band -> band.category == selectedCat.value }
                .toMutableList()
        }
    }

    fun onFavouriteClick(band: ListElement, isfav: Boolean){
        band.isFavourite = !isfav
    }

    fun onSwipe(index: Int){
        _bandList.value?.removeAt(index)
        _bandList.value = _bandList.value //this notifies observers that the value has changed
    }

    fun loadCategories(cat: Array<String>){ //loads categories from Rescources
        categories = cat
    }



    fun onCategorySelected(categoryPos: Int) {
        _selectedCat.value = categories[categoryPos]
    }

    fun getSelectedCatIndex(): Int {
        val filterArray =  categories
        for ((i, string) in filterArray.withIndex()){
            if (string == selectedCat.value) return i
        }
        return -1
    }
}


