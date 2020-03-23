package com.gleam.kiwi.model

data class Tasks(
    val tasks: List<Task>
)

data class Task(
    val id: Int,
    val date: String,
    val title: String,
    val color: String
)