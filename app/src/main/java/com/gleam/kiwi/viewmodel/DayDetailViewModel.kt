package com.gleam.kiwi.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gleam.kiwi.model.Task
import com.gleam.kiwi.model.TaskId
import com.gleam.kiwi.net.FetchResult
import com.gleam.kiwi.net.KiwiClient
import kotlinx.coroutines.launch

class DayDetailViewModel(val date: String, val client: KiwiClient) : ViewModel() {
    private val _taskList: MutableLiveData<List<Task>>? = MutableLiveData()
    val taskList: LiveData<List<Task>>?
        get() {
            return _taskList
        }

    init {
        setTaskList()
    }

    private fun setTaskList() {
        viewModelScope.launch {
            val tasks = when (val res = client.getTasks()) {
                is FetchResult.Success -> res.result
                else -> null
            }
            _taskList?.value = getSpecifiedDaysTasks(date, tasks?.tasks)
        }
    }

    private fun getSpecifiedDaysTasks(day: String, tasks: List<Task>?): List<Task>? {
        return tasks?.filter { it.date == day }
    }


    fun registerTask(task: String) {
        viewModelScope.launch {
            client.registerTask(Task("-1", date, task))
            setTaskList()
        }
    }

    fun deleteTask(position: Int) {
        viewModelScope.launch {
            taskList?.value?.get(position)?.let {
                client.removeTask(TaskId(it.id))
                setTaskList()
            }
        }
    }

    fun getTaskTitle(position: Int): String? {
        return taskList?.value?.get(position)?.title
    }
}
