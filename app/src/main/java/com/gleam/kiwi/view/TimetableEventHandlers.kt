package com.gleam.kiwi.view

import android.view.View

interface TimetableEventHandlers {
    fun onClick(view: View, dayOfWeek: TimetableEnum): Boolean
    fun onLongClick(view: View, dayOfWeek: TimetableEnum): Boolean
}