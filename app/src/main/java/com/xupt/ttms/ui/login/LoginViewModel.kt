package com.xupt.ttms.ui.login

import android.util.Patterns
import androidx.lifecycle.*
import com.xupt.ttms.data.LoginRepository

import com.xupt.ttms.R
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> = _loginResult

    private val _codeResult = MutableLiveData(false)
    val codeResult:LiveData<Boolean> = _codeResult

    fun login(username: String, password: String) = viewModelScope.launch {
        _loginResult.value = loginRepository.login(username, password)
    }

    fun getCode(phone:String) = viewModelScope.launch {
        _codeResult.value = loginRepository.getCode(phone)
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    private fun isUserNameValid(username: String): Boolean {
        return if (username.length == 11) {
            Patterns.PHONE.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length == 6
    }

}