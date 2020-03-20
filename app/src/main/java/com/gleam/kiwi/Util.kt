package com.gleam.kiwi

import android.content.Context
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import org.threeten.bp.DayOfWeek


fun daysOfWeek(): List<DayOfWeek> {
    return DayOfWeek.values().let {
        it.takeLast(1) + it.take(6)
    }
}


fun Context.getColorCompat(@ColorRes color: Int) = ContextCompat.getColor(this, color)
fun TextView.setTextColorRes(@ColorRes color: Int) = setTextColor(context.getColorCompat(color))