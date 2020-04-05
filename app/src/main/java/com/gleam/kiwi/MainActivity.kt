package com.gleam.kiwi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidThreeTen.init(this)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.myNavHostFragment)
        setupWithNavController(bottom_navigation, navController)
    }
}

