package com.xupt.ttms.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xupt.ttms.data.LoginDataSource
import kotlinx.coroutines.launch

class TokenViewModel:ViewModel() {
    private val _isLogin = MutableLiveData<Boolean>()
    val isLogin:LiveData<Boolean> = _isLogin

    private val loginDataSource = LoginDataSource()

    fun isLogin() = viewModelScope.launch {
        _isLogin.value = loginDataSource.isLogin()
    }

}