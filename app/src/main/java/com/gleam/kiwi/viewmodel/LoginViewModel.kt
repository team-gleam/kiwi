package com.gleam.kiwi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gleam.kiwi.model.User
import com.gleam.kiwi.net.KiwiClient
import com.gleam.kiwi.net.KiwiService
import com.gleam.kiwi.net.KiwiServiceInterFace
import com.gleam.kiwi.net.NetworkStatus
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val service = KiwiService().create(KiwiServiceInterFace::class.java)
    private val client = KiwiClient.getInstance(service)
    private val _loginStatus: MutableLiveData<NetworkStatus> = MutableLiveData()
    val loginStatus: LiveData<NetworkStatus>
        get() {
            return _loginStatus
        }

    fun signIn(username: String, password: String) {
        val user = User(username, password)
        viewModelScope.launch {
            _loginStatus.value = client.signIn(user)
        }
    }

    fun signUp(username: String, password: String) {
        val user = User(username, password)
        viewModelScope.launch {
            _loginStatus.value = client.signUp(user)
        }
    }
}
