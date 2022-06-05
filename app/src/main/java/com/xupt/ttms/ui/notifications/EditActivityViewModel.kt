package com.xupt.ttms.ui.notifications

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EditActivityViewModel : ViewModel() {

    private val _bitmap = MutableLiveData<Bitmap>()
    val bitmap:LiveData<Bitmap> = _bitmap

    fun getBitmap(bitmap: Bitmap) {
        _bitmap.value = bitmap
    }

}