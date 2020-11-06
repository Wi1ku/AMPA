package com.example.bmi

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Tools {
    fun splitIgnoreEmpty(string: String, vararg delimiters: String): List<String> {
            return string.split(*delimiters).filter {
                it.isNotEmpty()
            }
    }
}