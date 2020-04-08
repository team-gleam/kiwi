package com.gleam.kiwi.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gleam.kiwi.model.Timetable
import com.gleam.kiwi.net.*
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
            val res = client.getTimetable()
            Log.i("log-loadTimetable",res.toString())

            _timetable?.value = when(res){
                is NetworkStatusWithTimeTable.Success -> res.timetable
                else -> null
            }

        }
    }


}
