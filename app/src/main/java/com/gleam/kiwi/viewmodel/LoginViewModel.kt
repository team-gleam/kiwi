package com.gleam.kiwi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gleam.kiwi.model.User
import com.gleam.kiwi.net.KiwiClient
import com.gleam.kiwi.net.KiwiService
import com.gleam.kiwi.net.KiwiServiceInterFace
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val service = KiwiService().create(KiwiServiceInterFace::class.java)
    private val client = KiwiClient(service)

    fun signIn(username: String, password: String) {
        val user = User(username, password)
        viewModelScope.launch {
            client.signIn(user)
        }
    }

    fun signUp(username: String, password: String) {
        val user = User(username, password)
        viewModelScope.launch {
            client.signUp(user)
        }
    }
}
