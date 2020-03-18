package com.gleam.kiwi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.gleam.kiwi.R
import com.gleam.kiwi.viewModel.DayDetailViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class DayDetailFragment : Fragment() {
   // private val dayDetailViewModel by lazy { ViewModelProvider.NewInstanceFactory().create(DayDetailViewModel::class.java) }
    private val dayDetailViewModel: DayDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.day_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}
