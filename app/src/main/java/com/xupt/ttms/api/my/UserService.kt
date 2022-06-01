package com.xupt.ttms.api.my

import com.xupt.ttms.data.bean.userBean.user.UpdateReponse
import com.xupt.ttms.data.bean.userBean.user.UserResponse
import retrofit2.http.POST

interface UserService {

    @POST("user/inform/get")
    suspend fun getInformation():UserResponse

    @POST("user/inform/update")
    suspend fun postInformation():UpdateReponse

}