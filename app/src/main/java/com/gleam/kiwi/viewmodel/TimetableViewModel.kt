package com.gleam.kiwi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gleam.kiwi.model.Details
import com.gleam.kiwi.model.Timetable
import com.gleam.kiwi.net.*
import com.gleam.kiwi.view.TimetableEnum
import kotlinx.coroutines.launch

class TimetableViewModel(private val client: KiwiClient): ViewModel() {
    private var _timetable: MutableLiveData<Timetable?> = MutableLiveData()
    val timetable: LiveData<Timetable?>
        get(){
            return _timetable
        }

    init {
        loadTimetable()
    }

    private fun loadTimetable() {
        viewModelScope.launch {
            val res = client.getTimetable()
            _timetable.value = when(res){
                is NetworkStatusWithTimeTable.Success -> res.timetable
                else -> null
            }
        }
    }

    fun registerSubject(dayOfWeek: TimetableEnum, detail: Details){
        viewModelScope.launch {
            client.registerTimetable(getNewTimetable(dayOfWeek, detail))
            loadTimetable()
        }
    }

    private fun getNewTimetable(dayOfWeek: TimetableEnum, detail: Details): Timetable{
        var newTimetable: Timetable = timetable.value!!
        when(dayOfWeek){
            TimetableEnum.MON1 -> newTimetable.timetable.mon.first = detail
            TimetableEnum.MON2 -> newTimetable.timetable.mon.second = detail
            TimetableEnum.MON3 -> newTimetable.timetable.mon.third = detail
            TimetableEnum.MON4 -> newTimetable.timetable.mon.fourth = detail
            TimetableEnum.MON5 -> newTimetable.timetable.mon.fifth = detail

            TimetableEnum.TUE1 -> newTimetable.timetable.tue.first = detail
            TimetableEnum.TUE2 -> newTimetable.timetable.tue.second = detail
            TimetableEnum.TUE3 -> newTimetable.timetable.tue.third = detail
            TimetableEnum.TUE4 -> newTimetable.timetable.tue.fourth = detail
            TimetableEnum.TUE5 -> newTimetable.timetable.tue.fifth = detail

            TimetableEnum.WED1 -> newTimetable.timetable.wed.first = detail
            TimetableEnum.WED2 -> newTimetable.timetable.wed.second = detail
            TimetableEnum.WED3 -> newTimetable.timetable.wed.third = detail
            TimetableEnum.WED4 -> newTimetable.timetable.wed.fourth = detail
            TimetableEnum.WED5 -> newTimetable.timetable.wed.fifth = detail

            TimetableEnum.THU1 -> newTimetable.timetable.thu.first = detail
            TimetableEnum.THU2 -> newTimetable.timetable.thu.second = detail
            TimetableEnum.THU3 -> newTimetable.timetable.thu.third = detail
            TimetableEnum.THU4 -> newTimetable.timetable.thu.fourth = detail
            TimetableEnum.THU5 -> newTimetable.timetable.thu.fifth = detail

            TimetableEnum.FRI1 -> newTimetable.timetable.fri.first = detail
            TimetableEnum.FRI2 -> newTimetable.timetable.fri.second = detail
            TimetableEnum.FRI3 -> newTimetable.timetable.fri.third = detail
            TimetableEnum.FRI4 -> newTimetable.timetable.fri.fourth = detail
            TimetableEnum.FRI5 -> newTimetable.timetable.fri.fifth = detail

        }
        return newTimetable
    }

}
