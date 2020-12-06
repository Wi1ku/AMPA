package com.example.catalog

data class ListElement(val id: Int,
                       val name: String,
                       val category: String,
                       val image_id: Int,
                       val description: String,
                       val albums: List<Album>,
                       val images: List<Int>,
                       var isFavourite: Boolean)

