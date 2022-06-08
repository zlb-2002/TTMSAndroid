package com.xupt.ttms.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xupt.ttms.data.PageLimit
import com.xupt.ttms.data.bean.movieBean.DataSource
import com.xupt.ttms.data.source.MovieSource
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _list = MutableLiveData<MutableList<DataSource>>().apply {
        value = mutableListOf()
    }
    val list: LiveData<MutableList<DataSource>> = _list

    private var page:Int = 1

    private val movieSource = MovieSource()

    fun getList() = viewModelScope.launch {
        val map = hashMapOf(
            "sortType" to "rate",
            "sortRule" to "down",
            "page" to "$page",
            "pageLimit" to "${PageLimit.movieLimit}"
        )
        _list.value = _list.value.apply {
            this?.addAll(movieSource.getList(map))
        }
        page++
    }
}