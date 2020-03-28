package com.gleam.kiwi.view

import android.view.View
import android.widget.TextView
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.ViewContainer
import kotlinx.android.synthetic.main.calendar_day.view.*

class CalendarContainer(view: View) : ViewContainer(view) {
    lateinit var day: CalendarDay
    val textView: TextView = view.day_text

    init {
        view.setOnClickListener {
            if (day.owner == DayOwner.THIS_MONTH) {
                // nav to next fragment
            }
        }
    }
}
