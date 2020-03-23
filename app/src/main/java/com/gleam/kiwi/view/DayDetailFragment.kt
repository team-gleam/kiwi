package com.gleam.kiwi.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.gleam.kiwi.R
import com.gleam.kiwi.databinding.DaytitleFragmentBinding
import com.gleam.kiwi.view.recycler.TaskRecyclerAdapter
import com.gleam.kiwi.viewModel.DaytitleViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class DaytitleFragment : Fragment() {
   // private val daytitleViewModel: DaytitleViewModel by viewModel()
    private lateinit var daytitleViewModel: DaytitleViewModel
    private lateinit var daytitleFragmentBinding: DaytitleFragmentBinding
    private val date = "2020/03/21" //placeholder
    private lateinit var taskRecyclerAdapter: TaskRecyclerAdapter

    private fun testFragment(){
        Log.i("DaytitleFragment", "call testFragment()")
        daytitleViewModel = DaytitleViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("DaytitleFragment", "call onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        daytitleFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.day_title_fragment, container, false)
        daytitleFragmentBinding.lifecycleOwner = this

        taskRecyclerAdapter = TaskRecyclerAdapter(this@DaytitleFragment::onItemClick)

        daytitleFragmentBinding.taskRecyclerView.apply{
            adapter = taskRecyclerAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        return daytitleFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun onItemClick(click: View, position: Int){
        //TODO:implement viewModel.onItemClick
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        testFragment()
        observeViewModel(daytitleViewModel)
    }

    private fun observeViewModel(viewModel: DaytitleViewModel) {
        viewModel.taskList?.observe(viewLifecycleOwner, Observer { tasks ->
                tasks?.also {
                    Log.i("DaytitleFragment", daytitleViewModel.getDayTasks(tasks, date).toString())
                    taskRecyclerAdapter.setTasks(daytitleViewModel.getDayTasks(tasks, date))
                }
            }
        )
    }

}
