package com.gleam.kiwi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer

import com.gleam.kiwi.R
import com.gleam.kiwi.databinding.TimetableFragmentBinding
import com.gleam.kiwi.model.Details
import com.gleam.kiwi.viewmodel.TimetableViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class TimetableFragment() : Fragment(),TimetableEventHandlers,
    TimetableRegisterDialogFragment.TimetableRegisterDialogListener{

    private val viewModel: TimetableViewModel by viewModel()
    private lateinit var timetableFragmentBinding: TimetableFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        timetableFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.timetable_fragment, container, false)
        timetableFragmentBinding.viewModel = viewModel
        timetableFragmentBinding.handlers = this
        return timetableFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.timetable?.observe(viewLifecycleOwner, Observer { timetable ->
            timetable.also {

            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onClick(view: View, dayOfWeek: TimetableEnum) : Boolean {
        TimetableRegisterDialogFragment(dayOfWeek).show(childFragmentManager, "TimetableRegisterDialog")
        return true
    }

    override fun onRegisterClick(dialog: DialogFragment, detail: Details, dayOfWeek: TimetableEnum) {
        viewModel.registerSubject(dayOfWeek, detail)
    }

}
