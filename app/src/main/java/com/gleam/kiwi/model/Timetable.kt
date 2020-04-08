package com.gleam.kiwi.model

data class Timetable(
    val timetable: Structure
)

data class Structure(
    val mon: Mon,
    val tue: Tue,
    val wed: Wed,
    val thu: Thu,
    val fri: Fri
)

data class Mon(
    val first: Details?,
    val second: Details?,
    val third: Details?,
    val fourth: Details?,
    val fifth: Details?
)

data class Tue(
    val first: Details?,
    val second: Details?,
    val third: Details?,
    val fourth: Details?,
    val fifth: Details?
)

data class Wed(
    val first: Details?,
    val second: Details?,
    val third: Details?,
    val fourth: Details?,
    val fifth: Details?
)

data class Thu(
    val first: Details?,
    val second: Details?,
    val third: Details?,
    val fourth: Details?,
    val fifth: Details?
)

data class Fri(
    val first: Details?,
    val second: Details?,
    val third: Details?,
    val fourth: Details?,
    val fifth: Details?
)

data class Details(
    val subject: String,
    val room: String?
)