package com.gleam.kiwi.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gleam.kiwi.model.Task
import com.gleam.kiwi.model.Tasks
import com.gleam.kiwi.net.KiwiClient
import com.gleam.kiwi.net.KiwiService
import com.gleam.kiwi.net.KiwiServiceInterFace
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDate

class DayDetailViewModel(date: String) : ViewModel() {

    private lateinit var client: KiwiClient
    private val _taskList: MutableLiveData<List<Task>>? = MutableLiveData()
    val taskList: LiveData<List<Task>>?
        get() {
            return _taskList
        }


    init {
        loadTaskList(date)
    }

    private fun loadTaskList(date: String) {
        runBlocking {
            client = KiwiClient(KiwiService().create(KiwiServiceInterFace::class.java))
            val tasks: Tasks? = client.getTasks()
            _taskList?.postValue(getDateTasks(tasks?.tasks, date))
            //_taskList?.postValue(tasks?.tasks)
        }
    }

    private fun getDateTasks(tasks: List<Task>?, day: String): List<Task>? {
        return tasks?.filter { it.date == day }
    }


    fun registerTask(task: String){
        val date = LocalDate.now()
        viewModelScope.launch {
            client.registerTask(Task(-1,date.toString(),task))
        }
    }

    private fun getTask(index: Int): Task{
        return taskList?.value!![index]
    }

    fun deleteTask(index: Int){
        viewModelScope.launch {
            client.removeTask(getTask(index).id)
        }
    }

    fun getTaskTitle(index: Int): String {
        return getTask(index).title
    }

    fun onItemClick(view: View, position: Int){
        Log.i("onItemClick", view.toString()+position.toString())
    }



}
