package com.gleam.kiwi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gleam.kiwi.model.User
import com.gleam.kiwi.net.FetchResult
import com.gleam.kiwi.net.KiwiClient
import kotlinx.coroutines.launch

class LoginViewModel(private val client: KiwiClient) : ViewModel() {
    private val _loginStatus: MutableLiveData<FetchResult<Any?>> = MutableLiveData()
    val loginStatus: LiveData<FetchResult<Any?>>
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
