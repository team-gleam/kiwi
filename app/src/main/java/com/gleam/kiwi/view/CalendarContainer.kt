package com.gleam.kiwi.view

import android.util.Log
import android.view.View
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.ViewContainer
import kotlinx.android.synthetic.main.calendar_day.view.*

class CalendarContainer(view: View) : ViewContainer(view) {
    lateinit var day: CalendarDay
    val textView = view.DayText

    init {
        view.setOnClickListener {
            if (day.owner == DayOwner.THIS_MONTH) {
                Log.i("CalendarSelected", day.date.toString())
            }
        }
    }
}