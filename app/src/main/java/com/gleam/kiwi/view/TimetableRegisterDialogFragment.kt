package com.gleam.kiwi.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.gleam.kiwi.R
import com.gleam.kiwi.model.Details

class TimetableRegisterDialogFragment(dayOfWeek: TimetableEnum) : DialogFragment() {

    private lateinit var listener: TimetableRegisterDialogListener
    private val _dayOfWeek: TimetableEnum = dayOfWeek

    interface TimetableRegisterDialogListener {
        fun onRegisterClick(dialog: DialogFragment, detail: Details, dayOfWeek: TimetableEnum)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        when {
            context is TimetableRegisterDialogListener -> listener = context
            parentFragment is TimetableRegisterDialogListener -> listener = parentFragment as TimetableRegisterDialogListener
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Build the dialog and set up the button click handlers
            val builder = AlertDialog.Builder(activity, R.style.DialogTheme)
            val registerView = activity!!.layoutInflater.inflate(R.layout.timetable_register_dialog_fragment, null)

            builder.setView(registerView)
                .setTitle("Register subject")
                .setPositiveButton("register",
                    DialogInterface.OnClickListener { dialog, id ->
                        var detail = Details(registerView.findViewById<EditText>(R.id.subject).text.toString(),
                                            registerView.findViewById<EditText>(R.id.room).text.toString())
                        listener.onRegisterClick(this, detail, _dayOfWeek)
                    })
                .setNegativeButton("cancel") { _, _ -> }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onPause() {
        super.onPause()
        dismiss()
    }
}