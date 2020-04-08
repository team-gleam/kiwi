package com.gleam.kiwi.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.gleam.kiwi.R

class TaskDeleteDialogFragment(private val title: String) : DialogFragment() {

    var onDeleteClickListener: DialogInterface.OnClickListener? = null
    var onCancelClickListener: DialogInterface.OnClickListener? =
        DialogInterface.OnClickListener { _, _ -> }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity, R.style.DeleteDialogTheme)
        builder.setTitle("Delete Task")
            .setTitle(title)
            .setPositiveButton("DELETE", onDeleteClickListener)
            .setNegativeButton("CANCEL", onCancelClickListener)

        return builder.create()
    }

    override fun onPause() {
        super.onPause()
        dismiss()
    }
}