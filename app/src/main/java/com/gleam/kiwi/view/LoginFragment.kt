package com.gleam.kiwi.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
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
        viewModel = LoginViewModel()
        loginFragmentBinding.apply {
            signIn.setOnClickListener {
                viewModel.signIn(
                    userNameTextField.text.toString(),
                    passwordTextField.text.toString()
                )
            }
            signUp.setOnClickListener {
                viewModel.signUp(
                    userNameTextField.text.toString(),
                    passwordTextField.text.toString()
                )
            }
        }

        return loginFragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
