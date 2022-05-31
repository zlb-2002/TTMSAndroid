package com.xupt.ttms.util.tool

import android.content.Context
import android.widget.Toast

object ToastUtil {
    private var toast: Toast? = null
    fun getToast(context: Context?, theText: String?) {
        if (toast == null) {
            toast = Toast.makeText(context, theText, Toast.LENGTH_SHORT)
        } else {
            toast!!.setText(theText)
            toast!!.duration = Toast.LENGTH_SHORT
        }
        toast!!.show()
    }
}