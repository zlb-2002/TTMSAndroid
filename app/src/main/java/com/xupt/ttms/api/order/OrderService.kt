package com.xupt.ttms.api.order

import com.xupt.ttms.data.bean.order.OderResponse
import com.xupt.ttms.data.bean.userBean.login.LoginResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.math.BigInteger

interface OrderService {
    @GET("user/getOrder")
    suspend fun getOrder():OderResponse

    @POST("user/reverseOrder")
    suspend fun reverseOrder(@Body body: RequestBody): LoginResponse?
}