package com.gleam.kiwi.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.gleam.kiwi.R
import com.gleam.kiwi.ext.setBottomNavigationBar
import com.gleam.kiwi.ext.setTextColorRes
import com.gleam.kiwi.viewmodel.CalendarViewModel
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import kotlinx.android.synthetic.main.calendar_base.*
import kotlinx.android.synthetic.main.calendar_day.view.*
import kotlinx.android.synthetic.main.calendar_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.TextStyle
import java.util.*


class CalendarFragment : Fragment() {
    private val viewModel: CalendarViewModel by viewModel()
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
        setupDaysOfWeek()
        setupCalendar()
        setBottomNavigationBar(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.daysContainTask.observe(
            viewLifecycleOwner,
            Observer { taskList: List<LocalDate>? ->
                taskList?.forEach { task ->
                    calendar.notifyDateChanged(task)
                }
            })
        // disable back key
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() = Unit
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateTaskList()
        calendar.notifyCalendarChanged()
    }

    private fun setupDaysOfWeek() {
        day_of_week.children.mapNotNull { it as? TextView }
            .forEachIndexed { index, textView ->
                with(textView) {
                    text = SORTED_DAYS_OF_WEEK[index]
                        .getDisplayName(TextStyle.SHORT, Locale.ENGLISH).toUpperCase(Locale.ENGLISH)
                    setTextColorRes(R.color.white_light)
                }
            }
    }

    private fun setupCalendar() {
        val currentMonth = YearMonth.now()
        val oldestMonth = currentMonth.minusMonths(12)
        val newestMonth = currentMonth.plusMonths(12)
        with(calendar) {
            setup(oldestMonth, newestMonth, SORTED_DAYS_OF_WEEK.first())
            scrollToMonth(currentMonth)
            monthScrollListener = {
                year_text.text = it.yearMonth.year.toString()
                month_text.text = monthFormatter.format(it.yearMonth)
            }

            dayBinder = object : DayBinder<CalendarContainer> {
                override fun create(view: View) = CalendarContainer(view)
                override fun bind(container: CalendarContainer, day: CalendarDay) {
                    val date = day.date
                    container.day = day
                    container.textView.setOnClickListener {
                        val action =
                            CalendarFragmentDirections.actionCalendarFragmentToDayDetailFragment(
                                date.toString()
                            )
                        findNavController().navigate(action)
                    }
                    container.textView.apply {
                        text = date.dayOfMonth.toString()
                        setTextColorRes(R.color.white)
                        when {
                            day.owner == DayOwner.THIS_MONTH && day.date == today ->
                                setBackgroundResource(R.drawable.today_bg)
                            day.owner == DayOwner.THIS_MONTH -> setTextColorRes(R.color.white)
                            else -> setTextColorRes(R.color.white_light)
                        }
                    }
                    viewModel.daysContainTask.value?.let { daysContainTask ->
                        if (date in daysContainTask) {
                            container.view.notification_mark.setBackgroundResource(R.drawable.notification_yellow)
                        }
                    }
                }
            }
        }
    }

    companion object {
        private val SORTED_DAYS_OF_WEEK = DayOfWeek.values().let { dayOfWeek ->
            dayOfWeek.takeLast(1) + dayOfWeek.dropLast(1)
        }
    }

}
