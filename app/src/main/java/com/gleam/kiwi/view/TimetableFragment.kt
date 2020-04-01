package com.gleam.kiwi.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.gleam.kiwi.R
import com.gleam.kiwi.viewmodel.TimetableViewModel

class TimetableFragment : Fragment() {

    companion object {
        fun newInstance() = TimetableFragment()
    }

    private lateinit var viewModel: TimetableViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.timetable_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TimetableViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
