package com.gleam.kiwi.model

data class TimeTableInfo(
    val mon: Mon,
    val tue: Tue,
    val wed: Wed,
    val thu: Thu,
    val fri: Fri
)

data class Mon(
    val `1`: String?,
    val `2`: String?,
    val `3`: String?,
    val `4`: String?,
    val `5`: String?
)

data class Tue(
    val `1`: String?,
    val `2`: String?,
    val `3`: String?,
    val `4`: String?,
    val `5`: String?
)

data class Wed(
    val `1`: String?,
    val `2`: String?,
    val `3`: String?,
    val `4`: String?,
    val `5`: String?
)

data class Thu(
    val `1`: String?,
    val `2`: String?,
    val `3`: String?,
    val `4`: String?,
    val `5`: String?
)

data class Fri(
    val `1`: String?,
    val `2`: String?,
    val `3`: String?,
    val `4`: String?,
    val `5`: String?
)