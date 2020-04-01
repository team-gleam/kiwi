package com.gleam.kiwi.viewmodel

import androidx.lifecycle.ViewModel
import com.gleam.kiwi.net.KiwiClient
import com.gleam.kiwi.model.Timetable

class TimetableViewModel : ViewModel() {
    private lateinit var client: KiwiClient
    private lateinit var timetable: Timetable

    

}
