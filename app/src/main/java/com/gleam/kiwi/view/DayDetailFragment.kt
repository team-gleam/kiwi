package com.gleam.kiwi.view

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.gleam.kiwi.R
import com.gleam.kiwi.databinding.DayDetailFragmentBinding
import com.gleam.kiwi.view.recycler.TaskRecyclerAdapter
import com.gleam.kiwi.viewmodel.DayDetailViewModel
import kotlinx.android.synthetic.main.day_detail_fragment.*

class DayDetailFragment : Fragment() {
   // private val dayDetailViewModel: dayDetailViewModel by viewModel()
    private lateinit var dayDetailViewModel: DayDetailViewModel
    private lateinit var dayDetailFragmentBinding: DayDetailFragmentBinding
    private val date = "2020-03-30" //placeholder
    private lateinit var taskRecyclerAdapter: TaskRecyclerAdapter

    private fun testFragment(){
        Log.i("DayDetailFragment", "call testFragment()")
        dayDetailViewModel = DayDetailViewModel(date)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("DayDetailFragment", "call onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dayDetailFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.day_detail_fragment, container, false)
        dayDetailFragmentBinding.lifecycleOwner = this

        taskRecyclerAdapter = TaskRecyclerAdapter(this@DayDetailFragment::onItemClick)

        dayDetailFragmentBinding.taskRecyclerView.apply{
            adapter = taskRecyclerAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        dayDetailFragmentBinding.apply {
            register.setOnClickListener{
                dayDetailViewModel.registerTask(newTask.text.toString())
                newTask.text.clear()
                //close software keyboard
                val manager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(view?.windowToken, 0)
            }
        }
        return dayDetailFragmentBinding.root
    }

    private fun onItemClick(click: View, position: Int){
        //TODO:implement viewModel.onItemClick
        val dialog = TaskDeleteDialogFragment(dayDetailViewModel.getTaskTitle(position))
        dialog.onDeleteClickListener = DialogInterface.OnClickListener {
            dialog, id ->
            Log.i("dialog", "delete clicked")
            dayDetailViewModel.deleteTask(position)
        }
        dialog.show(childFragmentManager, "deleteTask")
     //   dayDetailViewModel.onItemClick(click, position)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        testFragment()
        observeViewModel(dayDetailViewModel)
    }

    private fun observeViewModel(viewModel: DayDetailViewModel) {
        viewModel.taskList?.observe(viewLifecycleOwner, Observer { tasks ->
                tasks?.also {
                    taskRecyclerAdapter.setTasks(it)
                }
            }
        )
    }

}
