package com.gleam.kiwi.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gleam.kiwi.model.Tasks
import com.gleam.kiwi.net.KiwiClient
import com.gleam.kiwi.net.KiwiService
import com.gleam.kiwi.net.KiwiServiceInterFace
import kotlinx.coroutines.runBlocking
import java.lang.Exception

class DayDetailViewModel() : ViewModel() {
    private val token = "placeholder"
    var taskList = MutableLiveData<Tasks>()

    init {
        loadTaskList()
    }

    private fun loadTaskList() {
        runBlocking {
            try {
                val client = KiwiClient(KiwiService().create(KiwiServiceInterFace::class.java))
                val tasks: Tasks? = client.getTasks(token)
                if (tasks != null){
                    //TODO: Pick task from tasks
                    taskList.postValue(tasks)
                }else {

                }
            } catch (e: Exception){
                e.stackTrace
            }
        }
    }

}
