package com.gleam.kiwi.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import com.gleam.kiwi.R
import com.gleam.kiwi.model.Day
import com.gleam.kiwi.model.LessonDetails
import kotlinx.android.synthetic.main.timetable_register_dialog_fragment.*
import kotlinx.android.synthetic.main.timetable_register_dialog_fragment.view.*

class TimetableRegisterDialogFragment(private val day: Day) : DialogFragment() {

    private lateinit var listener: TimetableRegisterDialogListener

    interface TimetableRegisterDialogListener {
        fun onRegisterClick(dialog: DialogFragment, day: Day, detail: LessonDetails)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        when {
            context is TimetableRegisterDialogListener -> listener = context
            parentFragment is TimetableRegisterDialogListener -> {
                listener = parentFragment as TimetableRegisterDialogListener
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val registerView = it.layoutInflater.inflate(
                R.layout.timetable_register_dialog_fragment,
                it.findViewById(R.id.content)
            )

            AlertDialog.Builder(it, R.style.DialogTheme)
                .setView(registerView)
                .setTitle("New subject")
                .setPositiveButton("register") { _, _ ->
                    val detail = LessonDetails(
                        registerView.subject?.text.toString(),
                        registerView.room?.text.toString(),
                        registerView.memo?.text.toString()
                    )
                    listener.onRegisterClick(this, day, detail)
                }
                .setNegativeButton("cancel") { _, _ -> }
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onPause() {
        super.onPause()
        dismiss()
    }
}