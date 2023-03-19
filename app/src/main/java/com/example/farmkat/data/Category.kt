package com.example.farmkat.data

sealed class Category(val category : String) {

    object Fruits : Category("Fruits")
    object Vegetables : Category("Vegetables")
    object Crops : Category("Crops")
    object Equipment : Category("Equipment")
    object Agrochemicals : Category("Agrochemicals")


}