package com.gleam.kiwi.model

data class TimeTableInfo(
    val timetable: Timetable
)

data class Timetable(
    val mon: Mon,
    val tue: Tue,
    val wed: Wed,
    val thu: Thu,
    val fri: Fri
)

data class Mon(
    val `1`: Details?,
    val `2`: Details?,
    val `3`: Details?,
    val `4`: Details?,
    val `5`: Details?
)

data class Tue(
    val `1`: Details?,
    val `2`: Details?,
    val `3`: Details?,
    val `4`: Details?,
    val `5`: Details?
)

data class Wed(
    val `1`: Details?,
    val `2`: Details?,
    val `3`: Details?,
    val `4`: Details?,
    val `5`: Details?
)

data class Thu(
    val `1`: Details?,
    val `2`: Details?,
    val `3`: Details?,
    val `4`: Details?,
    val `5`: Details?
)

data class Fri(
    val `1`: Details?,
    val `2`: Details?,
    val `3`: Details?,
    val `4`: Details?,
    val `5`: Details?
)

data class Details(
    val subject: String,
    val room: String?
)