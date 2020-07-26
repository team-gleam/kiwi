package com.gleam.kiwi.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.gleam.kiwi.R
import com.gleam.kiwi.ext.setBottomNavigationBar
import com.gleam.kiwi.model.User
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
        val user = User(extractUsername(), extractPassword())
        val validateUsernameResult = user.validateUsername()
        val validatePasswordResult = user.validatePassword()
        when {
            validateUsernameResult is ValidationResult.OK && validatePasswordResult is ValidationResult.OK ->
                viewModel.signIn(user)

            validateUsernameResult is ValidationResult.OK -> setErrorMessageForPasswordField(
                validatePasswordResult
            )
            validatePasswordResult is ValidationResult.OK -> setErrorMessageForUsernameField(
                validateUsernameResult
            )
            else -> {
                setErrorMessageForUsernameField(validateUsernameResult)
                setErrorMessageForPasswordField(validatePasswordResult)
            }
        }
    }

    private fun signUp() {
        val user = User(extractUsername(), extractPassword())
        val validateUsernameResult = user.validateUsername()
        val validatePasswordResult = user.validatePassword()
        when {
            validateUsernameResult is ValidationResult.OK && validatePasswordResult is ValidationResult.OK -> viewModel.signUp(
                user
            )
            validateUsernameResult is ValidationResult.OK -> setErrorMessageForPasswordField(
                validatePasswordResult
            )
            validatePasswordResult is ValidationResult.OK -> setErrorMessageForUsernameField(
                validateUsernameResult
            )
            else -> {
                setErrorMessageForUsernameField(validateUsernameResult)
                setErrorMessageForPasswordField(validatePasswordResult)
            }
        }
    }

    private fun loginStatusDistributor(status: FetchResult<Any?>) {
        when (status) {
            is FetchResult.Success -> findNavController().navigate(R.id.action_loginFragment_to_calendarFragment)
            is FetchResult.InvalidData -> Toast.makeText(
                context,
                "Username or Password is invalid",
                Toast.LENGTH_SHORT
            ).show()
            is FetchResult.InternalError -> Toast.makeText(
                context,
                "Internal Error",
                Toast.LENGTH_SHORT
            )
            is FetchResult.UnexpectedError -> Toast.makeText(context, "Error", Toast.LENGTH_SHORT)
                .show()
            is FetchResult.Timeout -> Toast.makeText(context, "Timeout", Toast.LENGTH_SHORT).show()
        }
    }

    private fun extractUsername(): String {
        return username_text_field.text.toString()
    }

    private fun extractPassword(): String {
        return password_text_field.text.toString()
    }

    private fun setErrorMessageForUsernameField(result: ValidationResult) {
        username_text_field.error = when (result) {
            is ValidationResult.NotAllowedPattern -> "Username is consists only of alphanumeric characters"
            is ValidationResult.TooShort -> "Username must be at least one character"
            is ValidationResult.TooLong -> "Username must be less than ${result.maxLength} characters long"
            else -> ""
        }
    }

    private fun setErrorMessageForPasswordField(result: ValidationResult) {
        password_text_field.error = when (result) {
            is ValidationResult.NotAllowedPattern -> "Password is consists only of alphanumeric characters"
            is ValidationResult.TooShort -> "Password must be at least eight character"
            is ValidationResult.TooLong -> "Password must be less than ${result.maxLength} characters long"
            else -> ""
        }
    }
}

sealed class ValidationResult {
    object OK : ValidationResult()
    object NotAllowedPattern : ValidationResult()
    object TooShort : ValidationResult()
    class TooLong(val maxLength: Int) : ValidationResult()
}
