package com.gleam.kiwi.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.gleam.kiwi.R
import com.gleam.kiwi.daysOfWeek
import com.gleam.kiwi.setTextColorRes
import com.gleam.kiwi.viewmodel.CalendarViewModel
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import kotlinx.android.synthetic.main.calendar_base.*
import kotlinx.android.synthetic.main.calendar_day.view.*
import kotlinx.android.synthetic.main.calendar_fragment.*
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.TextStyle
import java.util.*

class CalendarFragment : Fragment() {
    private lateinit var viewModel: CalendarViewModel
    private val today = LocalDate.now()
    private val monthFormatter = DateTimeFormatter.ofPattern("MMMM")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.calendar_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = CalendarViewModel()
        var dayContainTask: List<String> = listOf()
        viewModel.daysContainTask.observe(viewLifecycleOwner, androidx.lifecycle.Observer { t ->
            dayContainTask = t
            t.forEach { x ->
                calendar.notifyDateChanged(
                    LocalDate.parse(
                        x,
                        DateTimeFormatter.ISO_DATE
                    )
                )
            }
        })

        val currentMonth = YearMonth.now()
        val oldestMonth = currentMonth.minusMonths(12)
        val newestMonth = currentMonth.plusMonths(12)
        val daysOfWeek = daysOfWeek()

        day_of_week.children.forEachIndexed { index, v ->
            (v as TextView).apply {
                text = daysOfWeek[index].getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
                    .toUpperCase(Locale.ENGLISH)
                setTextColorRes(R.color.white_light)
            }
        }

        calendar.apply {
            setup(oldestMonth, newestMonth, daysOfWeek.first())
            scrollToMonth(currentMonth)
            monthScrollListener = {
                year_text.text = it.yearMonth.year.toString()
                month_text.text = monthFormatter.format(it.yearMonth)
            }

            dayBinder = object : DayBinder<CalendarContainer> {
                override fun create(view: View) = CalendarContainer(view)
                override fun bind(container: CalendarContainer, day: CalendarDay) {
                    container.day = day
                    container.textView.apply {
                        text = day.date.dayOfMonth.toString()
                        setTextColorRes(R.color.white)
                        if (day.owner == DayOwner.THIS_MONTH) {
                            if (day.date == today) {
                                setBackgroundResource(R.drawable.today_bg)
                            }
                        } else {
                            setTextColorRes(R.color.white_light)
                        }
                    }
                    if (dayContainTask.contains(day.date.toString())) {
                        container.view.notification_mark.setBackgroundResource(R.drawable.notification_yellow)
                    }
                }
            }
        }
    }
}
