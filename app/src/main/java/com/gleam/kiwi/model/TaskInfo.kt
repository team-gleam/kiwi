package com.gleam.kiwi.model

data class TaskInfo(
    val tasks: List<Task>
)

data class Task(
    val date: String,
    val detail: String
)