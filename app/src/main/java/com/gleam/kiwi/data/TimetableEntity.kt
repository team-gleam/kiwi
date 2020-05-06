package com.gleam.kiwi.data

import com.google.gson.annotations.SerializedName

data class TimetableEntity(
    @SerializedName("timetable") val dayLessonsMap: MutableMap<String, DayLessons>
)

data class DayLessons(
    @SerializedName("1") var first: LessonDetails?,
    @SerializedName("2") var second: LessonDetails?,
    @SerializedName("3") var third: LessonDetails?,
    @SerializedName("4") var fourth: LessonDetails?,
    @SerializedName("5") var fifth: LessonDetails?
)

data class LessonDetails(
    val subject: String,
    val room: String?,
    val memo: String?
)