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

class CalendarViewModel : ViewModel() {
    private val client = KiwiClient(KiwiService().create(KiwiServiceInterFace::class.java))
    private var taskList: Tasks? = null
    private var _daysContainTask: MutableLiveData<List<String>> = MutableLiveData()
    val daysContainTask: LiveData<List<String>>
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
        _daysContainTask.value = taskList?.tasks?.map { t -> t.date }
    }
}