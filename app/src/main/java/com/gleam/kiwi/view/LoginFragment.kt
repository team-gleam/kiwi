package com.gleam.kiwi.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.gleam.kiwi.R
import com.gleam.kiwi.databinding.LoginFragmentBinding
import com.gleam.kiwi.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.login_fragment.*

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
                    username_text_field.text.toString(),
                    passwordTextField.text.toString()
                )
            }
            signUp.setOnClickListener {
                viewModel.signUp(
                    username_text_field.text.toString(),
                    password_text_field.text.toString()
                )
            }
        }

        return loginFragmentBinding.root
    }

}
