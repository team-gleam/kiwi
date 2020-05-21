package com.gleam.kiwi.model

import com.google.gson.annotations.SerializedName

data class Timetable(
    val lessonMap: MutableMap<TimetableDayOfWeek, DayLessons> = mutableMapOf()
) {

    fun findLessonDetails(x: Int, y: Int): LessonDetails? {
        return Day(
            TimetableDayOfWeek.on(x)
                ?: return null,
            y.takeIf { it in 1..5 }
                ?: return null
        ).let { day -> findLessonDetails(day) }
    }

    fun findLessonDetails(day: Day): LessonDetails? =
        lessonMap[day.dayOfWeek]?.getLessonDetailsAt(day.period)

}

data class Day(
    val dayOfWeek: TimetableDayOfWeek,
    val period: Int
) {
    companion object {
        fun at(x: Int, y: Int): Day? {
            return Day(
                TimetableDayOfWeek.on(x)
                    ?: return null,
                y.takeIf { it in 1..5 }
                    ?: return null
            )
        }
    }
}

enum class TimetableDayOfWeek(val indexOnTimetable: Int) {
    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5);

    companion object {
        fun on(index: Int): TimetableDayOfWeek? =
            values().firstOrNull { it.indexOnTimetable == index }
    }

}

data class DayLessons(
    @SerializedName("1") var first: LessonDetails? = null,
    @SerializedName("2") var second: LessonDetails? = null,
    @SerializedName("3") var third: LessonDetails? = null,
    @SerializedName("4") var fourth: LessonDetails? = null,
    @SerializedName("5") var fifth: LessonDetails? = null
) {

    fun getLessonDetailsAt(period: Int): LessonDetails? = when (period) {
        1 -> first
        2 -> second
        3 -> third
        4 -> fourth
        5 -> fifth
        else -> null
    }

    fun setLessonDetailsAt(period: Int, details: LessonDetails) {
        when (period) {
            1 -> first = details
            2 -> second = details
            3 -> third = details
            4 -> fourth = details
            5 -> fifth = details
        }
    }

}

data class LessonDetails(
    val subject: String,
    val room: String?,
    val memo: String?
)