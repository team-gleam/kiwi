package com.gleam.kiwi.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gleam.kiwi.model.Task
import com.gleam.kiwi.model.Tasks
import com.gleam.kiwi.net.KiwiClient
import com.gleam.kiwi.net.KiwiService
import com.gleam.kiwi.net.KiwiServiceInterFace
import kotlinx.coroutines.runBlocking
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class DayDetailViewModel(private val day: String) : ViewModel() {
    private val token = "placeholder"
    var taskList = MutableLiveData<List<Task>>()

    init {
        loadTaskList()
    }

    private fun loadTaskList() {
        runBlocking {
            try {
                val client = KiwiClient(KiwiService().create(KiwiServiceInterFace::class.java))
                val tasks: Tasks? = client.getTasks(token)
                if (tasks != null){
                    taskList.postValue(getDayTasks(tasks, day))
                }else {
                }
            } catch (e: Exception){
                e.stackTrace
            }
        }
    }

   // 20XX-10-10
    private fun getDayTasks(tasks: Tasks, day: String): List<Task>{
        return tasks.tasks.filter{ it.date == day }
    }

}
