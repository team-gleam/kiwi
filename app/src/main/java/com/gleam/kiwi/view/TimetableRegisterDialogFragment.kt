package com.gleam.kiwi.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.gleam.kiwi.R
import com.gleam.kiwi.model.Details
import kotlinx.android.synthetic.main.timetable_register_dialog_fragment.*

class TimetableRegisterDialogFragment(private val dayOfWeek: TimetableEnum) : DialogFragment() {

    private lateinit var listener: TimetableRegisterDialogListener

    interface TimetableRegisterDialogListener {
        fun onRegisterClick(dialog: DialogFragment, detail: Details, dayOfWeek: TimetableEnum)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        when {
            context is TimetableRegisterDialogListener -> listener = context
            parentFragment is TimetableRegisterDialogListener -> listener =
                parentFragment as TimetableRegisterDialogListener
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val registerView = View.inflate(
                context,
                R.layout.timetable_register_dialog_fragment,
                null
            )

            AlertDialog.Builder(it, R.style.DialogTheme)
                .setView(registerView)
                .setTitle("New subject")
                .setPositiveButton(
                    "register"
                ) { _, _ ->
                    val detail = Details(
                        subject.text.toString(),
                        room.text.toString(),
                        memo.text.toString()
                    )
                    listener.onRegisterClick(this, detail, dayOfWeek)
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