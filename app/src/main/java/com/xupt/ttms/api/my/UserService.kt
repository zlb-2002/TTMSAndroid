package com.xupt.ttms.api.my

import com.xupt.ttms.data.bean.userBean.login.LoginResponse
import com.xupt.ttms.data.bean.userBean.user.UpdateReponse
import com.xupt.ttms.data.bean.userBean.user.UserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface UserService {

    @POST("user/inform/get")
    suspend fun getInformation():UserResponse

    @POST("user/inform/update")
    suspend fun postInformation(@Body body : RequestBody):UpdateReponse

    @POST("user/inform/updatePortrait")
    @Multipart
    suspend fun postPortrait(@Part body: MultipartBody.Part): LoginResponse

    @POST("user/addBalance")
    suspend fun postPay(@Body body: RequestBody):LoginResponse

}