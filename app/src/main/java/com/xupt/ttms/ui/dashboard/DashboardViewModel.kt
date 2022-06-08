package com.xupt.ttms.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xupt.ttms.data.bean.order.OderResponse
import com.xupt.ttms.data.source.OrderSource
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {

    private val _order by lazy { MutableLiveData<OderResponse>() }
    val order:LiveData<OderResponse>
        get() = _order

    private val orderSource = OrderSource()

    fun getOrder() = viewModelScope.launch {
        _order.value = orderSource.getOrder()
    }
}