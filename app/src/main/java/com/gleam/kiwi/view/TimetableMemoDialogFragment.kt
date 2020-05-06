package com.gleam.kiwi.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.gleam.kiwi.R

class TimetableMemoDialogFragment(private val memo: String?) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val message = SpannableString(memo ?: "").also {
            Linkify.addLinks(it, Linkify.WEB_URLS)
        }

        return activity?.let { it ->
            val builder = AlertDialog.Builder(activity, R.style.DialogTheme)
            val memoView =
                it.layoutInflater.inflate(R.layout.timetable_memo_dialog_fragment, null)
            memoView.findViewById<TextView>(R.id.message_text).text = message
            memoView.findViewById<TextView>(R.id.message_text).movementMethod =
                LinkMovementMethod.getInstance()
            builder.setView(memoView)
                .setTitle("Memo")
                .setNegativeButton("close") { _, _ -> }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onPause() {
        super.onPause()
        dismiss()
    }
}
