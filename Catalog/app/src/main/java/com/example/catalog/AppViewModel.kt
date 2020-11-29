package com.example.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppViewModel : ViewModel(){

    var clickedPosition = MutableLiveData<Int>()

    var bandList: MutableLiveData<MutableList<ListElement>> = MutableLiveData(mutableListOf(
        ListElement("Beatles", "rock", R.drawable.ic_launcher_background, "beatels"),
        ListElement("Fleetwood Mac", "rock", R.drawable.ic_launcher_background, "fm"),
        ListElement("Jimi Hendrix", "rock", R.drawable.ic_launcher_background, "JH"),
        ListElement("Miles Davis", "jazz", R.drawable.ic_launcher_foreground, "MD")))


    override fun onCleared() {
        super.onCleared()
    }
}