package com.xupt.ttms.data.source

import android.util.Log
import com.google.gson.Gson
import com.xupt.ttms.api.my.UserService
import com.xupt.ttms.data.bean.userBean.user.UserResponse
import com.xupt.ttms.util.retrofit.RetrofitManager

class UserSource {

    private val userService = RetrofitManager.createService(UserService::class.java)
    private val gson = Gson()

    suspend fun getUserInformation():UserResponse? = try {
            userService.getInformation()
        } catch (e:Exception) {
            null
        }

    suspend fun postUserInformation():Boolean = try {
        Log.d("TAG", "postUserInformation: ${userService.postInformation().data}")
        userService.postInformation().data
    } catch (e:Exception) {
        false
    }


}