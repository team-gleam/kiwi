package com.gleam.kiwi.view.recycler

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.task_list_cell.view.*

class TaskRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view){
    val titleTextView: TextView = view.title
}