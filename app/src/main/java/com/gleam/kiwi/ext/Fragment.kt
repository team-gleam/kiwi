package com.gleam.kiwi.ext

import android.view.View
import androidx.fragment.app.Fragment
import com.gleam.kiwi.R
import com.google.android.material.bottomnavigation.BottomNavigationView

fun Fragment.setBottomNavigationBar(visible: Boolean) {
    val navBar = activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)
    if (visible) {
        navBar?.visibility = View.VISIBLE
    } else {
        navBar?.visibility = View.INVISIBLE
    }
}