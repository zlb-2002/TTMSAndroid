package com.xupt.ttms.api.login

import com.xupt.ttms.data.bean.userBean.login.CodeResponse
import com.xupt.ttms.data.bean.userBean.login.LoginResponse
import okhttp3.Call
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST

interface LoginService {

    @POST("user/login/getCode/")
    suspend fun getCode(@Body body : RequestBody):CodeResponse

    @POST("user/login/judgeCode")
    suspend fun judgeCode(@Body body: RequestBody):LoginResponse

}