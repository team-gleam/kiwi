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
    var first: Details?,
    var second: Details?,
    var third: Details?,
    var fourth: Details?,
    var fifth: Details?
)

data class Tue(
    var first: Details?,
    var second: Details?,
    var third: Details?,
    var fourth: Details?,
    var fifth: Details?
)

data class Wed(
    var first: Details?,
    var second: Details?,
    var third: Details?,
    var fourth: Details?,
    var fifth: Details?
)

data class Thu(
    var first: Details?,
    var second: Details?,
    var third: Details?,
    var fourth: Details?,
    var fifth: Details?
)

data class Fri(
    var first: Details?,
    var second: Details?,
    var third: Details?,
    var fourth: Details?,
    var fifth: Details?
)

data class Details(
    val subject: String,
    val room: String?
)