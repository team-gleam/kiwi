package com.gleam.kiwi.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.gleam.kiwi.R
import com.gleam.kiwi.databinding.LoginFragmentBinding
import com.gleam.kiwi.viewModel.LoginViewModel

class LoginFragment : Fragment() {

    private lateinit var loginFragmentBinding: LoginFragmentBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.login_fragment, container, false)

        return loginFragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
