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
import com.gleam.kiwi.ext.disableBackKey
import com.gleam.kiwi.model.Day
import com.gleam.kiwi.model.LessonDetails
import com.gleam.kiwi.view.TimetableRegisterDialogFragment.TimetableRegisterDialogListener
import com.gleam.kiwi.viewmodel.TimetableViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class TimetableFragment : Fragment(), TimetableEventHandlers,
    TimetableRegisterDialogListener {

    private val timetableViewModel: TimetableViewModel by viewModel()
    private lateinit var timetableFragmentBinding: TimetableFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        timetableFragmentBinding =
            DataBindingUtil.inflate<TimetableFragmentBinding>(
                inflater, R.layout.timetable_fragment, container, false
            ).also {
                it.viewModel = timetableViewModel
                it.handlers = this@TimetableFragment
                it.lifecycleOwner = viewLifecycleOwner
            }
        return timetableFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        disableBackKey()
    }

    override fun onLongClick(view: View, x: Int, y: Int): Boolean {
        val day = Day.at(x, y) ?: return false
        TimetableRegisterDialogFragment(day).show(
            childFragmentManager,
            "TimetableRegisterDialog"
        )

        return true
    }

    override fun onClick(view: View, x: Int, y: Int): Boolean {
        val lessonDetails = Day.at(x, y)
            ?.let { day -> timetableViewModel.timetable.value?.findLessonDetails(day) }

        TimetableMemoDialogFragment(lessonDetails?.memo).show(
            childFragmentManager,
            "TimetableMemoDialog"
        )

        return true
    }

    override fun onRegisterClick(dialog: DialogFragment, day: Day, detail: LessonDetails) =
        timetableViewModel.registerSubject(day, detail)

}
