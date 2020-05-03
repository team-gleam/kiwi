package com.gleam.kiwi.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.gleam.kiwi.R
import com.gleam.kiwi.databinding.TimetableFragmentBinding
import com.gleam.kiwi.model.Details
import com.gleam.kiwi.viewmodel.TimetableViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class TimetableFragment : Fragment(), TimetableEventHandlers,
    TimetableRegisterDialogFragment.TimetableRegisterDialogListener {

    private val timetableViewModel: TimetableViewModel by viewModel()
    private lateinit var timetableFragmentBinding: TimetableFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        timetableFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.timetable_fragment, container, false)
        timetableFragmentBinding.apply {
            viewModel = timetableViewModel
            handlers = this@TimetableFragment
            lifecycleOwner = viewLifecycleOwner
        }

        return timetableFragmentBinding.root
    }

    override fun onLongClick(view: View, dayOfWeek: TimetableEnum): Boolean {
        TimetableRegisterDialogFragment(dayOfWeek).show(
            childFragmentManager,
            "TimetableRegisterDialog"
        )
        return true
    }

    override fun onClick(view: View, dayOfWeek: TimetableEnum): Boolean {
        TimetableMemoDialogFragment(timetableViewModel.extractMemoFromTimetable(dayOfWeek)).show(
            childFragmentManager,
            "TimetableMemoDialog"
        )
        return true
    }

    override fun onRegisterClick(
        dialog: DialogFragment,
        detail: Details,
        dayOfWeek: TimetableEnum
    ) {
        timetableViewModel.registerSubject(dayOfWeek, detail)
    }

}
