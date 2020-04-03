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
import com.gleam.kiwi.net.NetworkStatus
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
        sign_in.setOnClickListener { signIn() }
        sign_up.setOnClickListener { signUp() }
        viewModel.loginStatus.observe(viewLifecycleOwner, Observer { status ->
            loginStatusDistributor(status)
        })
    }

    private fun signIn() {
        viewModel.signIn(
            extractUsersInputFromUsernameTextField(),
            extractUsersInputFromPasswordTextField()
        )
    }

    private fun signUp() {
        viewModel.signUp(
            extractUsersInputFromUsernameTextField(),
            extractUsersInputFromPasswordTextField()
        )
    }

    private fun loginStatusDistributor(status: NetworkStatus) {
        when (status) {
            NetworkStatus.Success -> findNavController().navigate(R.id.action_loginFragment_to_calendarFragment)
            NetworkStatus.NotFound -> Toast.makeText(
                context,
                "Account Not Found",
                Toast.LENGTH_SHORT
            ).show()
            NetworkStatus.Error -> Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            NetworkStatus.Timeout -> Toast.makeText(context, "Timeout", Toast.LENGTH_SHORT).show()
        }
    }

    private fun extractUsersInputFromUsernameTextField(): String {
        return username_text_field.text.toString()
    }

    private fun extractUsersInputFromPasswordTextField(): String {
        return password_text_field.text.toString()
    }

}
