package com.gleam.kiwi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gleam.kiwi.net.KiwiClient
import com.gleam.kiwi.model.Timetable
import com.gleam.kiwi.net.KiwiService
import com.gleam.kiwi.net.KiwiServiceInterFace
import kotlinx.coroutines.launch

class TimetableViewModel : ViewModel() {
    private lateinit var client: KiwiClient
    private var _timetable: MutableLiveData<Timetable>? = MutableLiveData()
    val timetable: LiveData<Timetable>?
        get(){
            return _timetable
        }

    init {
        loadTimetable()
    }

    private fun loadTimetable() {
        viewModelScope.launch {
            client = KiwiClient(KiwiService().create(KiwiServiceInterFace::class.java))
            _timetable?.setValue(client.getTimetable())
        }
    }


}
