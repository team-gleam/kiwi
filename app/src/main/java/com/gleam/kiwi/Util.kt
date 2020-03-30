package com.gleam.kiwi

import android.content.Context
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun Context.getColorCompat(@ColorRes color: Int) = ContextCompat.getColor(this, color)
fun TextView.setTextColorRes(@ColorRes color: Int) = setTextColor(context.getColorCompat(color))
