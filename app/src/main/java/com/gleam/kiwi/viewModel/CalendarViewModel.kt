package com.gleam.kiwi.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gleam.kiwi.model.Tasks
import com.gleam.kiwi.net.KiwiClient
import com.gleam.kiwi.net.KiwiService
import com.gleam.kiwi.net.KiwiServiceInterFace
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate

class CalendarViewModel : ViewModel() {
    private val client = KiwiClient(KiwiService().create(KiwiServiceInterFace::class.java))
    private var taskList: Tasks? = null
    init {
        setTaskList()
    }
    private fun setTaskList() {
        viewModelScope.launch {
            taskList = client.getTasks()
        }
    }

    fun getDaysContainTask(): Map<String, String> {
        val res = mutableMapOf<String, String>()
        taskList?.tasks?.forEach{t -> res[t.date] = t.color}
        return res
    }
}