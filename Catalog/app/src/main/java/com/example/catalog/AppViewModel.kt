package com.example.catalog

import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppViewModel : ViewModel(), AdapterView.OnItemSelectedListener {

    private val _bandList: MutableLiveData<MutableList<ListElement>> = MutableLiveData(mutableListOf(
        ListElement(1,"Beatles", "Rock", R.drawable.ic_launcher_background, "beatels"),
        ListElement(2,"Fleetwood Mac", "Rock", R.drawable.ic_launcher_background, "fm"),
        ListElement(3,"Jimi Hendrix", "Rock", R.drawable.ic_launcher_background, "JH"),
        ListElement(4,"Miles Davis", "Jazz", R.drawable.ic_launcher_foreground, "MD")))
    var bandList: LiveData<MutableList<ListElement>> = _bandList


    fun getFilteredBands(category: String): LiveData<MutableList<ListElement>> {
        val list = bandList
        list.value?.filter { band -> band.category ==  category}
        return list
    }


    override fun onCleared() {
        super.onCleared()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent != null) {
            bandList = getFilteredBands(parent.getItemAtPosition(position).toString())
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }


}
