package com.example.bmi

class Tools {
    fun splitIgnoreEmpty(string: String, vararg delimiters: String): List<String> {
            return string.split(*delimiters).filter {
                it.isNotEmpty()
            }
    }
}