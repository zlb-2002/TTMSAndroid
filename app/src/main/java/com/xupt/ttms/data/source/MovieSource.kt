package com.xupt.ttms.data.source

import android.util.Log
import com.google.gson.Gson
import com.xupt.ttms.api.movie.MovieService
import com.xupt.ttms.data.bean.movieBean.DataSource
import com.xupt.ttms.data.bean.movieBean.MovieResponse
import com.xupt.ttms.util.retrofit.RetrofitManager

class MovieSource {

    private val movieService = RetrofitManager.createService(MovieService::class.java)
    private val gson = Gson()

    suspend fun getList(map:HashMap<String, String>):MutableList<DataSource> = try {
        //Log.d("TAG", "getList: ${movieService.getMovieList(map).data.dataSource.toMutableList()}")
        movieService.getMovieList(map).data.dataSource.toMutableList()
    } catch (e:Exception) {
        e.printStackTrace()
        mutableListOf()
    }
}