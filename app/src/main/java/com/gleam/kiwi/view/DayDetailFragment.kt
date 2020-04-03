package com.gleam.kiwi.view

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.gleam.kiwi.R
import com.gleam.kiwi.view.recycler.TaskRecyclerAdapter
import com.gleam.kiwi.viewmodel.DayDetailViewModel
import kotlinx.android.synthetic.main.day_detail_fragment.*

class DayDetailFragment : Fragment() {
    private lateinit var dayDetailViewModel: DayDetailViewModel
    private lateinit var taskRecyclerAdapter: TaskRecyclerAdapter
    private val action: DayDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.day_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dayDetailViewModel = DayDetailViewModel(action.content)
        taskRecyclerAdapter = TaskRecyclerAdapter(this@DayDetailFragment::onItemClick)

        taskRecyclerView.apply {
            adapter = taskRecyclerAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        register.setOnClickListener {
            taskRegister()
        }

        dayDetailViewModel.taskList?.observe(viewLifecycleOwner, Observer { tasks ->
            tasks?.also { taskRecyclerAdapter.setTasks(it) }
        })
    }

    private fun onItemClick(position: Int) {
        dayDetailViewModel.getTaskTitle(position)?.let { title ->
            TaskDeleteDialogFragment(title).apply {
                onDeleteClickListener = DialogInterface.OnClickListener { _, _ ->
                    dayDetailViewModel.deleteTask(position)
                }
            }.show(childFragmentManager, "DeleteTask")
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeViewModel(dayDetailViewModel)
    }

    private fun observeViewModel(viewModel: DayDetailViewModel) {
        viewModel.taskList?.observe(viewLifecycleOwner, Observer { tasks ->
            tasks?.let {
                taskRecyclerAdapter.setTasks(it)
            }
        }
        )
    }

    private fun taskRegister() {
        dayDetailViewModel.registerTask(newTask.text.toString())
        newTask.text.clear()
        val manager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}
