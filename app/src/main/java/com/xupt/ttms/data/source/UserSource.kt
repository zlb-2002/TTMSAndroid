package com.xupt.ttms.data.source

import android.graphics.Bitmap
import android.util.Log
import com.google.gson.Gson
import com.xupt.ttms.api.my.UserService
import com.xupt.ttms.data.bean.userBean.user.Data
import com.xupt.ttms.data.bean.userBean.user.UserResponse
import com.xupt.ttms.util.retrofit.RetrofitManager
import com.xupt.ttms.util.tool.StringAndBitmap
import okhttp3.MediaType.Companion.parse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class UserSource {

    private val userService = RetrofitManager.createService(UserService::class.java)
    private val gson = Gson()

    suspend fun getUserInformation():UserResponse? = try {
            userService.getInformation()
        } catch (e:Exception) {
            null
        }

    suspend fun postUserInformation(data: Data):Boolean = try {
        userService.postInformation(
            gson.toJson(data).toRequestBody("application/json".toMediaTypeOrNull())
        ).data
    } catch (e:Exception) {
        false
    }

    suspend fun postUserPortrait(bitmap:Bitmap):Boolean = try {
        val file = StringAndBitmap.getFile(bitmap)
        val body = MultipartBody.Part.createFormData("file", file.name, file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        )
        Log.d("TAG", "postUserPortrait: ${userService.postPortrait(body).data}")
        userService.postPortrait(body).data
    } catch (e:Exception) {
        false
    }


}