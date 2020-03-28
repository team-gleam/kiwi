package com.gleam.kiwi.viewmodel

import androidx.lifecycle.ViewModel
import com.gleam.kiwi.model.Tasks
import com.gleam.kiwi.net.KiwiClient
import com.gleam.kiwi.net.KiwiService
import com.gleam.kiwi.net.KiwiServiceInterFace
import kotlinx.coroutines.runBlocking

class CalendarViewModel : ViewModel() {
    private val client = KiwiClient(KiwiService().create(KiwiServiceInterFace::class.java))
    private var taskList: Tasks? = null

    init {
        setTaskList()
    }

    private fun setTaskList() {
        runBlocking {
            taskList = client.getTasks()
        }
    }

    fun getDaysContainTask(): List<String> {
        return taskList?.tasks?.map { t ->
            t.date
        } ?: listOf()
    }
}