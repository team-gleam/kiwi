package com.gleam.kiwi.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.SpannableString
import android.text.util.Linkify
import androidx.fragment.app.DialogFragment
import com.gleam.kiwi.R

class TimetableMemoDialogFragment(private val memo: String?) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity, R.style.DialogTheme)
        val message = SpannableString(memo ?: "").also {
            Linkify.addLinks(it, Linkify.WEB_URLS)
        }

        builder.setTitle("Memo")
            .setMessage(message)
            .setNegativeButton("close") { _, _ -> }
            .create()
        return builder.create()
    }

    override fun onPause() {
        super.onPause()
        dismiss()
    }
}
