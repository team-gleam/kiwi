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
    private val _loginStatus: MutableLiveData<FetchResult<Unit>> = MutableLiveData()
    val loginStatus: LiveData<FetchResult<Unit>>
        get() {
            return _loginStatus
        }

    fun signIn(user: User) {
        viewModelScope.launch {
            _loginStatus.value = client.signIn(user)
        }
    }

    fun signUp(user: User) {
        viewModelScope.launch {
            when (val result = client.signUp(user)) {
                is FetchResult.Success -> signIn(user)
                else -> {
                    _loginStatus.value = result
                }
            }
        }
    }
}
