package com.xupt.ttms.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xupt.ttms.data.PageLimit
import com.xupt.ttms.data.bean.movieBean.DataSource
import com.xupt.ttms.data.bean.scheduleBean.Schedule
import com.xupt.ttms.data.source.ScheduleSource
import kotlinx.coroutines.launch

class ScheduleViewModel:ViewModel() {

    private val _schedule = MutableLiveData<MutableList<Schedule>>().apply {
        value = mutableListOf()
    }
    val schedule: LiveData<MutableList<Schedule>> = _schedule

    private var movieId = ""

    fun setMovieId(movieId:String) {
        Log.d("TAG", "setMovieId: $movieId")
        this.movieId = movieId
    }

    private var page = 1
    private val scheduleSource = ScheduleSource()

    fun getList() = viewModelScope.launch {
        val map = hashMapOf("movieId" to movieId,
        "sortName" to "schedule_start_time",
        "sortRule" to "down",
        "page" to "$page",
        "pageLimit" to "5")
        _schedule.value = _schedule.value.apply {
            this?.addAll(scheduleSource.getList(map))
        }
        Log.d("TAG", "getList: ${_schedule.value}")
        page++
    }

}