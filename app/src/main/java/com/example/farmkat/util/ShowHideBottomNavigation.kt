package com.example.farmkat.util

import android.view.View
import androidx.fragment.app.Fragment
import com.example.farmkat.R
import com.example.farmkat.activities.ShoppingActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

fun Fragment.hideBottomNavigationView(){
    val bottomNavigationView =
        (activity as ShoppingActivity).findViewById<BottomNavigationView>(
            com.example.farmkat.R.id.bottomNavigation
        )
    bottomNavigationView.visibility = android.view.View.GONE
}

fun Fragment.showBottomNavigationView(){
    val bottomNavigationView =
        (activity as ShoppingActivity).findViewById<BottomNavigationView>(
            com.example.farmkat.R.id.bottomNavigation
        )
    bottomNavigationView.visibility = android.view.View.VISIBLE
}