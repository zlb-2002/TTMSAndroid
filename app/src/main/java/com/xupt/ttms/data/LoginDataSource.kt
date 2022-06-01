package com.xupt.ttms.data

import com.google.gson.Gson
import com.xupt.ttms.api.login.LoginService
import com.xupt.ttms.data.bean.userBean.login.CodeRequest
import com.xupt.ttms.data.bean.userBean.login.LoginRequest
import com.xupt.ttms.util.retrofit.RetrofitManager
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody

class LoginDataSource {

    private val loginService = RetrofitManager.createLoginService(LoginService::class.java)
    private val gson = Gson()

    suspend fun login(username: String, password: String): Boolean {
        return requestLogin(username, password)
    }

    fun logout() {
        // TODO: revoke authentication
    }

    suspend fun getCode(phone: String): Boolean {
        return requestCode(phone)
    }

    private suspend fun requestCode (phone: String) :Boolean {
        return try {
            val result = loginService.getCode(RequestBody.create("application/json".toMediaTypeOrNull(),gson.toJson(CodeRequest(phone))))
            result.data
        } catch (e:Exception) {
            false
        }
    }

    private suspend fun requestLogin(phone:String, code:String) :Boolean {
        return try{
            val result = loginService.judgeCode(RequestBody.create("application/json".toMediaTypeOrNull(), gson.toJson(LoginRequest(phone, code))))
            result.data
        } catch (e: Exception) {
            false
        }
    }

}