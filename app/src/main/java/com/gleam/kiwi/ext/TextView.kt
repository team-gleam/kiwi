package com.gleam.kiwi.ext

import android.widget.TextView
import androidx.annotation.ColorRes

fun TextView.setTextColorRes(@ColorRes color: Int) = setTextColor(context.getColorCompat(color))