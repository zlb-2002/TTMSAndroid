package com.xupt.ttms.data

import com.google.gson.Gson
import com.xupt.ttms.api.login.LoginService
import com.xupt.ttms.data.bean.userBean.login.CodeRequest
import com.xupt.ttms.data.bean.userBean.login.LoginRequest
import com.xupt.ttms.util.retrofit.RetrofitManager
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class LoginDataSource {

    private val loginService = RetrofitManager.createLoginService(LoginService::class.java)
    private val isLoginService = RetrofitManager.createService(LoginService::class.java)
    private val gson = Gson()

    suspend fun login(username: String, password: String): Boolean {
        return requestLogin(username, password)
    }

    suspend fun getCode(phone: String): Boolean {
        return requestCode(phone)
    }

    private suspend fun requestCode (phone: String) :Boolean {
        return try {
            val result = loginService.getCode(
                gson.toJson(CodeRequest(phone))
                    .toRequestBody("application/json".toMediaTypeOrNull())
            )
            result.data
        } catch (e:Exception) {
            false
        }
    }

    private suspend fun requestLogin(phone:String, code:String) :Boolean {
        return try{
            val result = loginService.judgeCode(
                gson.toJson(LoginRequest(phone, code))
                    .toRequestBody("application/json".toMediaTypeOrNull())
            )
            result.data
        } catch (e: Exception) {
            false
        }
    }

    suspend fun isLogin() = try {
        isLoginService.isLogin().data
    } catch (e:Exception) {
        false
    }

}