package com.xupt.ttms.util.tool

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Base64
import java.io.*

object StringAndBitmap {

    fun getFile(bitmap: Bitmap): File {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos)
        val file = File(Environment.getExternalStorageDirectory().toString() + "/temp.jpg")
        try {
            file.createNewFile()
            val fos = FileOutputStream(file)
            val `is`: InputStream = ByteArrayInputStream(baos.toByteArray())
            var x = 0
            val b = ByteArray(1024 * 100)
            while (`is`.read(b).also { x = it } != -1) {
                fos.write(b, 0, x)
            }
            fos.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return file
    }
}
