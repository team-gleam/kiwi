package com.gleam.kiwi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gleam.kiwi.viewModel.DayDetailViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

}