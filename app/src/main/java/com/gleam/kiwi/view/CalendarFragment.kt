package com.gleam.kiwi.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.gleam.kiwi.R
import com.gleam.kiwi.databinding.CalendarFragmentBinding
import com.gleam.kiwi.daysOfWeek
import com.gleam.kiwi.setTextColorRes
import com.gleam.kiwi.viewModel.CalendarViewModel
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import kotlinx.android.synthetic.main.calendar_base.*
import kotlinx.android.synthetic.main.calendar_fragment.*
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.TextStyle
import java.util.*

class CalendarFragment : Fragment() {
    private lateinit var viewModel: CalendarViewModel
    private lateinit var calendarFragmentBinding: CalendarFragmentBinding
    private val today = LocalDate.now()
    private val monthFormatter = DateTimeFormatter.ofPattern("MMMM")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        calendarFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.calendar_fragment, container, false)
        return calendarFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(12)
        val endMonth = currentMonth.plusMonths(12)
        val daysOfWeek = daysOfWeek()
        dayofweek.children.forEachIndexed { index, v ->
            (v as TextView).apply {
                text = daysOfWeek[index].getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
                    .toUpperCase(Locale.ENGLISH)
                setTextColorRes(R.color.white_light)
            }
        }

        calendar.apply {
            setup(startMonth, endMonth, daysOfWeek.first())
            scrollToMonth(currentMonth)
            monthScrollListener = {
                yearText.text = it.yearMonth.year.toString()
                monthText.text = monthFormatter.format(it.yearMonth)
            }
            dayBinder = object : DayBinder<CalendarContainer> {
                override fun create(view: View) = CalendarContainer(view)
                override fun bind(container: CalendarContainer, day: CalendarDay) {
                    container.day = day
                    container.textView.apply {
                        text = day.date.dayOfMonth.toString()
                        if (day.owner == DayOwner.THIS_MONTH) {
                            when (today) {
                                day.date -> {
                                    setTextColorRes(R.color.white)
                                    setBackgroundResource(R.drawable.today_bg)
                                }
                                else -> {
                                    setTextColorRes(R.color.white)
                                }
                            }
                        } else {
                            setTextColorRes(R.color.white_light)
                        }
                    }
                }
            }

        }

    }
}
