package com.xupt.ttms.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xupt.ttms.data.bean.order.Data
import com.xupt.ttms.data.bean.userBean.login.LoginResponse
import com.xupt.ttms.data.source.OrderSource
import kotlinx.coroutines.launch

class OrderViewModel :ViewModel(){

    private val _order by lazy { MutableLiveData<Data>() }
    val order:LiveData<Data>
        get() = _order

    private val _reverseOrder by lazy { MutableLiveData<LoginResponse>() }
    val reverseOrder:LiveData<LoginResponse>
        get() = _reverseOrder

    fun setData(data:Data) {
        _order.value = data
    }

    private val orderSource = OrderSource()

    fun reverseOrder() = viewModelScope.launch {
        _reverseOrder.value = order.value?.orderId?.let { orderSource.reverseOrder(it) }
    }

}