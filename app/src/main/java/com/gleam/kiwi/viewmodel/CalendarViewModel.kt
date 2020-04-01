package com.gleam.kiwi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gleam.kiwi.model.Tasks
import com.gleam.kiwi.net.KiwiClient
import com.gleam.kiwi.net.KiwiService
import com.gleam.kiwi.net.KiwiServiceInterFace
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

class CalendarViewModel : ViewModel() {
    private val client = KiwiClient(KiwiService().create(KiwiServiceInterFace::class.java))
    private var taskList: Tasks? = null
    private var _daysContainTask: MutableLiveData<List<LocalDate>> = MutableLiveData()
    val daysContainTask: LiveData<List<LocalDate>>
        get() {
            return _daysContainTask
        }

    init {
        setTaskList()
    }

    private fun setTaskList() {
        viewModelScope.launch {
            taskList = client.getTasks()
            setDaysContainTask()
        }
    }

    private fun setDaysContainTask() {
        _daysContainTask.value =
            taskList?.tasks?.map { t -> LocalDate.parse(t.date, DateTimeFormatter.ISO_DATE) }
    }
}