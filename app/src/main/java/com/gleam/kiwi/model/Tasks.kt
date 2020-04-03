package com.gleam.kiwi.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tasks(
    val tasks: List<Task>
) : Parcelable

@Parcelize
data class Task(
    val id: Int,
    val date: String,
    val title: String
) : Parcelable