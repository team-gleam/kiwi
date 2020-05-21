package com.gleam.kiwi.view

import android.view.View

interface TimetableEventHandlers {
    fun onClick(view: View, x: Int, y: Int): Boolean

    // this method must return Boolean; ref. http://www.ownway.info/Blog/2017/04/post-48.html
    fun onLongClick(view: View, x: Int, y: Int): Boolean
}