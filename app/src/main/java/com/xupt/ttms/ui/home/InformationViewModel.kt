package com.xupt.ttms.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xupt.ttms.data.bean.movieBean.DataSource

class InformationViewModel: ViewModel() {
    private val _dataSource = MutableLiveData<DataSource>()
    val dataSource: LiveData<DataSource> = _dataSource

    fun getDataSource(dataSource: DataSource) {
        _dataSource.value = dataSource
    }

}