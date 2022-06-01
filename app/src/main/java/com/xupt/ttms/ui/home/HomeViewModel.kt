package com.xupt.ttms.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xupt.ttms.data.bean.movieBean.DataSource

class HomeViewModel : ViewModel() {

    private val _list = MutableLiveData<MutableList<DataSource>>().apply {
        value = mutableListOf()
    }
    val list: LiveData<MutableList<DataSource>> = _list
}