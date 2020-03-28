package com.gleam.kiwi.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class TaskDeleteDialogFragment: DialogFragment() {

    var onDeleteClickListener: DialogInterface.OnClickListener? = null
    var onCancelClickListener: DialogInterface.OnClickListener? = DialogInterface.OnClickListener { _, _ -> }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("delete task")
            .setMessage("delete")
            .setPositiveButton("delete",onDeleteClickListener)
            .setNegativeButton("cancel",onCancelClickListener)

        return builder.create()
    }

    override fun onPause(){
        super.onPause()
        dismiss()
    }
}