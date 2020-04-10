package com.gleam.kiwi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer

import com.gleam.kiwi.R
import com.gleam.kiwi.databinding.TimetableFragmentBinding
import com.gleam.kiwi.viewmodel.TimetableViewModel

class TimetableFragment : Fragment() {

    private lateinit var viewModel: TimetableViewModel
    private lateinit var timetableFragmentBinding: TimetableFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = TimetableViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        timetableFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.timetable_fragment, container, false)
        timetableFragmentBinding.viewModel = viewModel
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


}
