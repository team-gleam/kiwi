package com.gleam.kiwi.data

import com.gleam.kiwi.model.DayLessons
import com.gleam.kiwi.model.Timetable
import com.gleam.kiwi.model.TimetableDayOfWeek
import com.google.gson.annotations.SerializedName

data class TimetableEntity(
    @SerializedName("timetable") val dayLessonsMap: MutableMap<String, DayLessons>
) {

    fun toTimetable(): Timetable = Timetable(
        mutableMapOf(
            TimetableDayOfWeek.MONDAY to dayLessonsMap.getOrDefault("mon", DayLessons()),
            TimetableDayOfWeek.TUESDAY to dayLessonsMap.getOrDefault("tue", DayLessons()),
            TimetableDayOfWeek.WEDNESDAY to dayLessonsMap.getOrDefault("wed", DayLessons()),
            TimetableDayOfWeek.THURSDAY to dayLessonsMap.getOrDefault("thu", DayLessons()),
            TimetableDayOfWeek.FRIDAY to dayLessonsMap.getOrDefault("fri", DayLessons())
        )
    )

}

fun Timetable.toEntity(): TimetableEntity = TimetableEntity(
    mutableMapOf(
        "mon" to this.lessonMap.getOrDefault(TimetableDayOfWeek.MONDAY, DayLessons()),
        "tue" to this.lessonMap.getOrDefault(TimetableDayOfWeek.TUESDAY, DayLessons()),
        "wed" to this.lessonMap.getOrDefault(TimetableDayOfWeek.WEDNESDAY, DayLessons()),
        "thu" to this.lessonMap.getOrDefault(TimetableDayOfWeek.THURSDAY, DayLessons()),
        "fri" to this.lessonMap.getOrDefault(TimetableDayOfWeek.FRIDAY, DayLessons())
    )
)
