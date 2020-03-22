package com.gleam.kiwi.view.recycler

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gleam.kiwi.R
import com.gleam.kiwi.model.Task

class TaskRecyclerAdapter(
    private val onItemCLick: (view: View, position: Int) -> Unit
) : RecyclerView.Adapter<TaskRecyclerViewHolder>() {

    private lateinit var recyclerView: RecyclerView
    private var taskList = emptyList<Task>()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onBindViewHolder(holder: TaskRecyclerViewHolder, position: Int){
        holder.apply {
            dateTextView.text = taskList[position].date
            detailTextView.text = taskList[position].detail
        }
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    fun setTasks(tasks: List<Task>){
        this.taskList = tasks
        Log.i("Adapter", this.taskList.toString())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskRecyclerViewHolder {
        Log.i("Adapter", "onCreateViewHolder")
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.task_list_cell, parent, false)
            .apply{
                setOnClickListener { view ->
                    onItemCLick(
                        view,
                        recyclerView.getChildAdapterPosition(view)
                    )
                }
            }

        return TaskRecyclerViewHolder(view)
    }


}