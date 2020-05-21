package com.gleam.kiwi.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.gleam.kiwi.R
import com.gleam.kiwi.ext.setBottomNavigationBar
import com.gleam.kiwi.net.FetchResult
import com.gleam.kiwi.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.login_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel


class LoginFragment : Fragment() {
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        button_signIn.setOnClickListener { signIn() }
        button_signUp.setOnClickListener { signUp() }
        viewModel.loginStatus.observe(viewLifecycleOwner, Observer { status ->
            loginStatusDistributor(status)
        })

        setBottomNavigationBar(false)
    }

    private fun signIn() {
        viewModel.signIn(
            extractUsername(),
            extractPassword()
        )
    }

    private fun signUp() {
        viewModel.signUp(
            extractUsername(),
            extractPassword()
        )
    }

    private fun loginStatusDistributor(status: FetchResult<Any?>) {
        when (status) {
            is FetchResult.Success -> findNavController().navigate(R.id.action_loginFragment_to_calendarFragment)
            is FetchResult.NotFound -> Toast.makeText(
                context,
                "Account Not Found",
                Toast.LENGTH_SHORT
            ).show()
            is FetchResult.Unexpected -> Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            is FetchResult.Timeout -> Toast.makeText(context, "Timeout", Toast.LENGTH_SHORT).show()
        }
    }

    private fun extractUsername(): String {
        return username_text_field.text.toString()
    }

    private fun extractPassword(): String {
        return password_text_field.text.toString()
    }

}
