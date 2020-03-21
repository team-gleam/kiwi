package com.gleam.kiwi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager

import com.gleam.kiwi.R
import com.gleam.kiwi.databinding.DayDetailFragmentBinding
import com.gleam.kiwi.view.recycler.TaskRecyclerAdapter
import com.gleam.kiwi.viewModel.DayDetailViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class DayDetailFragment : Fragment() {
    private val dayDetailViewModel: DayDetailViewModel by viewModel()
    private lateinit var dayDetailFragmentBinding: DayDetailFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dayDetailFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.day_detail_fragment, container, false)

        val taskRecyclerAdapter = TaskRecyclerAdapter(this@DayDetailFragment::onItemClick)
        val recyclerView = dayDetailFragmentBinding.taskRecyclerView.apply{
            adapter = taskRecyclerAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        return dayDetailFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun onItemClick(click: View, position: Int){
        //TODO:implement viewModel.onItemClick
    }

}
