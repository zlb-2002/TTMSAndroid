package com.xupt.ttms.ui.notifications

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.*
import com.xupt.ttms.data.bean.userBean.user.CombineResult
import com.xupt.ttms.data.bean.userBean.user.Data
import com.xupt.ttms.data.bean.userBean.user.UserResponse
import com.xupt.ttms.data.source.UserSource
import kotlinx.coroutines.launch

class NotificationsViewModel : ViewModel() {

    private val _userInformation = MutableLiveData<UserResponse?>()
    val userInformation: LiveData<UserResponse?> = _userInformation

    private val _isCommit = MutableLiveData<Boolean>()
    val isCommit:LiveData<Boolean> = _isCommit

    private val _commitInformation = MutableLiveData<Boolean>()
    val commitInformation:LiveData<Boolean> = _commitInformation

    private val _commitPortrait = MutableLiveData<Boolean>()
    val commitPortrait:LiveData<Boolean> = _commitPortrait

    private val _commitResult = MutableLiveData(0)
    val commitResult:LiveData<Int> = _commitResult

    private val userSource = UserSource()

    fun getUserInformation() = viewModelScope.launch {
        _userInformation.value = userSource.getUserInformation()
    }

    fun postUserInformation(data: Data) = viewModelScope.launch {
        _commitInformation.value = userSource.postUserInformation(data)
    }

    fun postUserPortrait(bitmap: Bitmap) = viewModelScope.launch {
        _commitInformation.value = userSource.postUserPortrait(bitmap)
    }


    fun nameChange(name:String) {
        _userInformation.value?.data?.username = name
    }

    fun ageChange(age:Int) {
        _userInformation.value?.data?.age = age
    }

    fun genderChange(gender:String) {
        when (gender) {
            "男" -> _userInformation.value?.data?.gender = 0.toString()
            "女" -> _userInformation.value?.data?.gender = 1.toString()
            else -> _userInformation.value?.data?.gender = 0.toString()

        }
    }

    fun emailChange(email:String) {
        _userInformation.value?.data?.email = email
    }

    fun introduceChange(introduce:String) {
        _userInformation.value?.data?.introduce = introduce
    }

    fun isCommit() {
        val data = userInformation.value?.data
        _isCommit.value = data?.email != null && data.gender != null && data.username != "未命名用户"
        Log.d("TAG", "isCommit: $data")
    }

    fun success() {
        _commitResult.value = _commitResult.value?.plus(1)
    }

    fun reset() {
        _commitResult.value = 0
    }

}