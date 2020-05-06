package com.gleam.kiwi.ext

import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.gleam.kiwi.R
import com.google.android.material.bottomnavigation.BottomNavigationView

fun Fragment.setBottomNavigationBar(visible: Boolean) {
    activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)?.visibility =
        if (visible) {
            View.VISIBLE
        } else {
            View.GONE
        }
}

fun Fragment.disableBackKey() {
    activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() = Unit
    })
}