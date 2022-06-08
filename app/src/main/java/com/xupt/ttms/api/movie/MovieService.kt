package com.xupt.ttms.api.movie

import com.xupt.ttms.data.bean.movieBean.MovieResponse
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface MovieService {

    @GET("movie/getList")
    suspend fun getMovieList(@QueryMap map:HashMap<String, String>):MovieResponse

}