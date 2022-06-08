package com.xupt.ttms.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xupt.ttms.data.bean.userBean.login.LoginResponse
import com.xupt.ttms.data.bean.userBean.user.PayRequest
import com.xupt.ttms.data.source.UserSource
import kotlinx.coroutines.launch

class PayViewModel:ViewModel() {

    private val _payResponse by lazy { MutableLiveData<LoginResponse>() }
    val payResponse:LiveData<LoginResponse>
        get() = _payResponse

    private val userSource = UserSource()

    private var userId = 0

    fun pay(text:String) = viewModelScope.launch {
        _payResponse.value = userSource.postPay(PayRequest(text.toDouble(), userId))
    }

    fun getUserId(id:Int) {
        userId = id
    }

}