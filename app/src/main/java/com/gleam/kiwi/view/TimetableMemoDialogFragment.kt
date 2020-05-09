package com.gleam.kiwi.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import androidx.fragment.app.DialogFragment
import com.gleam.kiwi.R
import kotlinx.android.synthetic.main.timetable_memo_dialog_fragment.view.*

class TimetableMemoDialogFragment(private val memo: String?) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val message = SpannableString(memo ?: "").also {
            Linkify.addLinks(it, Linkify.WEB_URLS)
        }

        return activity?.let { it ->
            val memoView =
                it.layoutInflater.inflate(
                    R.layout.timetable_memo_dialog_fragment,
                    it.findViewById(R.id.content)
                ).also {
                    it.message_text.apply {
                        text = message
                        movementMethod = LinkMovementMethod.getInstance()
                    }
                }
            AlertDialog.Builder(activity, R.style.DialogTheme)
                .setView(memoView)
                .setTitle("Memo")
                .setNegativeButton("close") { _, _ -> }
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onPause() {
        super.onPause()
        dismiss()
    }
}
