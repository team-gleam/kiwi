package com.gleam.kiwi.di

import android.app.Application
import com.gleam.kiwi.net.KiwiClient
import com.gleam.kiwi.net.KiwiService
import com.gleam.kiwi.net.KiwiServiceInterFace
import com.gleam.kiwi.viewmodel.CalendarViewModel
import com.gleam.kiwi.viewmodel.LoginViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class Application : Application() {
    private val module = module {
        viewModel { LoginViewModel(get()) }
        viewModel { CalendarViewModel(get()) }
        single { KiwiService().create(KiwiServiceInterFace::class.java) }
        single { KiwiClient(get()) }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(module)
        }
    }
}