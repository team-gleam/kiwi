package com.gleam.kiwi.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gleam.kiwi.model.Task
import com.gleam.kiwi.model.Tasks
import com.gleam.kiwi.net.KiwiClient
import com.gleam.kiwi.net.KiwiService
import com.gleam.kiwi.net.KiwiServiceInterFace
import kotlinx.coroutines.runBlocking

class DayDetailViewModel(private val day: String) : ViewModel() {
    private val token = "placeholder"
   // var taskList = MutableLiveData<List<Task>>()
    private val _taskList: MutableLiveData<List<Task>> = MutableLiveData()
    private val taskList: LiveData<List<Task>>
        get() {
            return _taskList
        }

    init {
        loadTaskList()
    }

    private fun loadTaskList() {
        runBlocking {
            val client = KiwiClient(KiwiService().create(KiwiServiceInterFace::class.java))
            val tasks: Tasks? = client.getTasks(token)
            if (tasks != null) {
                _taskList.postValue(getDayTasks(tasks, day))
            } else {
            }
        }
    }

    // 20XX-10-10
    private fun getDayTasks(tasks: Tasks, day: String): List<Task> {
        return tasks.tasks.filter { it.date == day }
    }

}
