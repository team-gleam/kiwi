package com.gleam.kiwi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gleam.kiwi.model.Details
import com.gleam.kiwi.model.Timetable
import com.gleam.kiwi.net.KiwiClient
import com.gleam.kiwi.net.NetworkStatusWithTimeTable
import com.gleam.kiwi.view.TimetableEnum
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
                is NetworkStatusWithTimeTable.Success -> res.timetable
                else -> null
            }
        }
    }

    fun registerSubject(dayOfWeek: TimetableEnum, detail: Details) {
        viewModelScope.launch {
            setTimetable(dayOfWeek, detail)
            timetable.value?.let { client.registerTimetable(it) }
            loadTimetable()
        }
    }

    private fun setTimetable(dayOfWeek: TimetableEnum, detail: Details) {
        when (dayOfWeek) {
            TimetableEnum.MON1 -> timetable.value?.timetable?.mon?.first = detail
            TimetableEnum.MON2 -> timetable.value?.timetable?.mon?.second = detail
            TimetableEnum.MON3 -> timetable.value?.timetable?.mon?.third = detail
            TimetableEnum.MON4 -> timetable.value?.timetable?.mon?.fourth = detail
            TimetableEnum.MON5 -> timetable.value?.timetable?.mon?.fifth = detail

            TimetableEnum.TUE1 -> timetable.value?.timetable?.tue?.first = detail
            TimetableEnum.TUE2 -> timetable.value?.timetable?.tue?.second = detail
            TimetableEnum.TUE3 -> timetable.value?.timetable?.tue?.third = detail
            TimetableEnum.TUE4 -> timetable.value?.timetable?.tue?.fourth = detail
            TimetableEnum.TUE5 -> timetable.value?.timetable?.tue?.fifth = detail

            TimetableEnum.WED1 -> timetable.value?.timetable?.wed?.first = detail
            TimetableEnum.WED2 -> timetable.value?.timetable?.wed?.second = detail
            TimetableEnum.WED3 -> timetable.value?.timetable?.wed?.third = detail
            TimetableEnum.WED4 -> timetable.value?.timetable?.wed?.fourth = detail
            TimetableEnum.WED5 -> timetable.value?.timetable?.wed?.fifth = detail

            TimetableEnum.THU1 -> timetable.value?.timetable?.thu?.first = detail
            TimetableEnum.THU2 -> timetable.value?.timetable?.thu?.second = detail
            TimetableEnum.THU3 -> timetable.value?.timetable?.thu?.third = detail
            TimetableEnum.THU4 -> timetable.value?.timetable?.thu?.fourth = detail
            TimetableEnum.THU5 -> timetable.value?.timetable?.thu?.fifth = detail

            TimetableEnum.FRI1 -> timetable.value?.timetable?.fri?.first = detail
            TimetableEnum.FRI2 -> timetable.value?.timetable?.fri?.second = detail
            TimetableEnum.FRI3 -> timetable.value?.timetable?.fri?.third = detail
            TimetableEnum.FRI4 -> timetable.value?.timetable?.fri?.fourth = detail
            TimetableEnum.FRI5 -> timetable.value?.timetable?.fri?.fifth = detail
        }
    }
}
