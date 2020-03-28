package com.gleam.kiwi.viewModel

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gleam.kiwi.model.Task
import com.gleam.kiwi.model.Tasks
import com.gleam.kiwi.net.KiwiClient
import com.gleam.kiwi.net.KiwiService
import com.gleam.kiwi.net.KiwiServiceInterFace
import kotlinx.coroutines.runBlocking
import java.time.LocalDate

class DayDetailViewModel(date: String) : ViewModel() {

   // var taskList = MutableLiveData<List<Task>>()
    private val _taskList: MutableLiveData<List<Task>>? = MutableLiveData()
    val taskList: LiveData<List<Task>>?
        get() {
            return _taskList
        }

    init {
        Log.i("DayDetailViewModel","DayDetailViewModel Initialize!!")
        loadTaskList(date)
    }

    private fun loadTaskList(date: String) {
        runBlocking {
           // val client = KiwiClient(KiwiService().create(KiwiServiceInterFace::class.java))
           // val tasks: Tasks? = client.getTasks(token)
            val tasks: Tasks? = createTestData()
            Log.i("DayDetailViewModel", tasks.toString())
            _taskList?.postValue(getDayTasks(tasks?.tasks, date))
            //_taskList?.postValue(tasks?.tasks)
        }
    }

    // 20XX-10-10
    fun getDayTasks(tasks: List<Task>?, day: String): List<Task>? {
        return tasks?.filter { it.date == day }
    }


    fun registerTask(task: String){
        //TODO: Use KiwiClient instance
        val date = LocalDate.now()
        //client.registerTasks(Task(-1,date.toString(),task,"#FFFFFF"))
    }

    fun onItemClick(view: View, position: Int){
        Log.i("onItemClick", view.toString()+position.toString())
    }


    private fun createTestData(): Tasks{
        var taskList = mutableListOf<Task>()
        for (i in 1..4) {
            val task: Task = Task(
                1,
                "2020/03/21",
                "hogehgoe",
                "#FFFFFF"
            )
            taskList.add(task)
        }
        for (i in 1..3) {
            val task: Task = Task(
                2,
                "2020/03/30",
                "hoge",
                "#000000"
            )
            taskList.add(task)
        }
        return Tasks(taskList)
    }

}
