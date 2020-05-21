package com.gleam.kiwi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gleam.kiwi.model.Day
import com.gleam.kiwi.model.LessonDetails
import com.gleam.kiwi.model.Timetable
import com.gleam.kiwi.net.FetchResult
import com.gleam.kiwi.net.KiwiClient
import kotlinx.coroutines.launch

class TimetableViewModel(private val client: KiwiClient) : ViewModel() {
    private var _timetable: MutableLiveData<Timetable?> = MutableLiveData()
    val timetable: LiveData<Timetable?>
        get() {
            return _timetable
        }

    init {
        loadTimetable()
    }

    private fun loadTimetable() {
        viewModelScope.launch {
            val res = client.getTimetable()
            _timetable.value = when (res) {
                is FetchResult.Success -> res.result
                else -> null
            }
        }
    }

    fun registerSubject(day: Day, details: LessonDetails) {
        viewModelScope.launch {
            setTimetable(day, details)
            timetable.value?.let { client.registerTimetable(it) }
            loadTimetable()
        }
    }

    private fun setTimetable(day: Day, details: LessonDetails) {
        timetable.value?.lessonMap?.get(day.dayOfWeek)?.setLessonDetailsAt(day.period, details)
    }

}
