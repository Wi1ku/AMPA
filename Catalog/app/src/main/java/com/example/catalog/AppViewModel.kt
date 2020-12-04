package com.example.catalog

import android.app.Application
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppViewModel() : ViewModel() {

    private val _selectedCat: MutableLiveData<String> = MutableLiveData("")
    var selectedCat: LiveData<String> = _selectedCat

    private val _bandList: MutableLiveData<MutableList<ListElement>> = MutableLiveData(mutableListOf(
        ListElement(1,"Beatles", "Rock", R.drawable.ic_launcher_background, "beatels", false),
        ListElement(2,"Fleetwood Mac", "Rock", R.drawable.ic_launcher_background, "fm",false),
        ListElement(3,"Jimi Hendrix", "Rock", R.drawable.ic_launcher_background, "JH",false),
        ListElement(4,"Miles Davis", "Jazz", R.drawable.ic_launcher_foreground, "MD",false)))
    var bandList: LiveData<MutableList<ListElement>> = _bandList


    fun getFilteredBands(): MutableList<ListElement> {
        return if(selectedCat.value == "All Categories") {
            bandList.value!!
        } else if (selectedCat.value == "Favourites"){
            bandList.value!!.filter { band -> band.isFavourite }.toMutableList()
        } else
            bandList.value!!.filter { band -> band.category ==  selectedCat.value}.toMutableList()
    }

    fun onFavouriteClick(band: ListElement, isfav: Boolean){
        band.isFavourite = !isfav
    }


    override fun onCleared() {
        super.onCleared()
    }

    fun onCategorySelected(categoryPos: Int) {
        _selectedCat.value = MainActivity.context.resources.getStringArray(R.array.filters)[categoryPos]
    }

    fun getSelectedCatIndex(): Int {
        val filterArray =  MainActivity.context.resources.getStringArray(R.array.filters)
        for ((i, string) in filterArray.withIndex()){
            if (string == selectedCat.value) return i
        }
        return -1
    }
}
