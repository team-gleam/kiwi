package com.gleam.kiwi.viewModel

import androidx.lifecycle.ViewModel
import com.gleam.kiwi.model.User
import com.gleam.kiwi.net.KiwiClient
import com.gleam.kiwi.net.KiwiService
import com.gleam.kiwi.net.KiwiServiceInterFace
import kotlinx.coroutines.runBlocking

class LoginViewModel : ViewModel() {
    private val service = KiwiService().create(KiwiServiceInterFace::class.java)
    private val client = KiwiClient(service)

    fun signIn(username: String, password: String) {
        val user = User(username, password)
        runBlocking {
            client.signIn(user)
        }
    }

    fun signUp(username: String, password: String) {
        val user = User(username, password)
        runBlocking {
            client.signUp(user)
        }
    }
}
