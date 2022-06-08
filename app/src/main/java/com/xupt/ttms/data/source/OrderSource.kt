package com.xupt.ttms.data.source

import android.util.Log
import com.google.gson.Gson
import com.xupt.ttms.api.order.OrderService
import com.xupt.ttms.data.bean.order.OderResponse
import com.xupt.ttms.data.bean.order.ReverseOrderRequest
import com.xupt.ttms.data.bean.userBean.login.LoginResponse
import com.xupt.ttms.util.retrofit.RetrofitManager
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.math.BigInteger

class OrderSource {
    private val orderService = RetrofitManager.createService(OrderService::class.java)
    private val gson = Gson()
    suspend fun getOrder():OderResponse? = try {
        orderService.getOrder()
    }catch (e:Exception){
        null
    }

    suspend fun reverseOrder(orderId:Long):LoginResponse? = try {
        Log.d("TAG", "reverseOrder: ")
        orderService.reverseOrder(gson.toJson(ReverseOrderRequest(orderId)).toRequestBody("application/json".toMediaTypeOrNull()))
    } catch(e:Exception) {
        Log.d("TAG", "reverseOrder: $e")
        null
    }

}