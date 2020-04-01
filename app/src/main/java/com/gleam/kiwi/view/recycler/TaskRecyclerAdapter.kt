package com.gleam.kiwi.view.recycler

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
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
            titleTextView.text = taskList[position].title
        }
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    fun setTasks(tasks: List<Task>){
        this.taskList = tasks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskRecyclerViewHolder {
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