package com.gleam.kiwi.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.gleam.kiwi.R
import com.gleam.kiwi.model.Details

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
            val builder = AlertDialog.Builder(activity, R.style.DialogTheme)
            val registerView =
                it.layoutInflater.inflate(R.layout.timetable_register_dialog_fragment, null)

            builder.setView(registerView)
                .setTitle("New subject")
                .setPositiveButton(
                    "register"
                ) { _, _ ->
                    val detail = Details(
                        registerView.findViewById<EditText>(R.id.subject).text.toString(),
                        registerView.findViewById<EditText>(R.id.room).text.toString(),
                        registerView.findViewById<EditText>(R.id.memo).text.toString()
                    )
                    listener.onRegisterClick(this, detail, dayOfWeek)
                }
                .setNegativeButton("cancel") { _, _ -> }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onPause() {
        super.onPause()
        dismiss()
    }
}