package com.gleam.kiwi.view

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.gleam.kiwi.R
import kotlinx.android.synthetic.main.timetable_memo_dialog_fragment.view.*
import org.w3c.dom.Text

class TimetableMemoDialogFragment(private val memo: String?) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val message = SpannableString(memo ?: "").also {
            Linkify.addLinks(it, Linkify.WEB_URLS)
        }

        return activity?.let { it ->
            val builder = AlertDialog.Builder(activity, R.style.DialogTheme)
            val memoView =
                it.layoutInflater.inflate(R.layout.timetable_memo_dialog_fragment, null)
            memoView.findViewById<TextView>(R.id.messageText).text = message
            memoView.findViewById<TextView>(R.id.messageText).movementMethod = LinkMovementMethod.getInstance()
            builder.setView(memoView)
                .setTitle("Memo")
                .setNegativeButton("close") { _, _ -> }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")

//        val builder = AlertDialog.Builder(activity, R.style.DialogTheme)
//
//        val textView = TextView(context)
//        textView.text = memo
//        Linkify.addLinks(textView, Linkify.ALL)
//        textView.setTextColor(Color.WHITE)
//        textView.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
//        textView.movementMethod = LinkMovementMethod.getInstance()
//
//        val sv = ScrollView(context)
//        sv.addView(textView)
//
//        builder.setTitle("Memo")
//            .setNegativeButton("close") { _, _ -> }
//            .setView(sv)
//            .create()
//        return builder.create()

    }

    override fun onPause() {
        super.onPause()
        dismiss()
    }
}
