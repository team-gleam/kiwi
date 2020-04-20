package com.gleam.kiwi.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.gleam.kiwi.R

class TaskDeleteDialogFragment(private val content: String) : DialogFragment() {

    lateinit var onDeleteClickListener: DialogInterface.OnClickListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity, R.style.DialogTheme)
        builder.setTitle("Delete Task?")
            .setMessage(content)
            .setPositiveButton("delete", onDeleteClickListener)
            .setNegativeButton("cancel") { _, _ -> }

        return builder.create()
    }

    override fun onPause() {
        super.onPause()
        dismiss()
    }
}