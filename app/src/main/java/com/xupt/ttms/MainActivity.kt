package com.xupt.ttms

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.xupt.ttms.databinding.ActivityMainBinding
import com.xupt.ttms.ui.notifications.EditActivityViewModel
import com.xupt.ttms.util.retrofit.RetrofitManager
import com.xupt.ttms.util.tool.ToastUtil

class MainActivity : AppCompatActivity() {

    private val CHOOSE_PHOTO: Int = 2
    private lateinit var binding: ActivityMainBinding
    val REQUEST_IMAGE_CAPTURE = 1

    private var mainViewModel: Lazy<EditActivityViewModel> = lazy { ViewModelProvider(this)[EditActivityViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mainToolbar)

        val navView: BottomNavigationView = binding.navView

        RetrofitManager.context = this

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            mainViewModel.value.getBitmap(data?.extras?.get("data") as Bitmap)
        } else if (requestCode == CHOOSE_PHOTO && resultCode == RESULT_OK) {
            if (resultCode == RESULT_OK) {

                data?.let { handleImageOnKitKat(it) }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openAlbum()
            } else {
                ToastUtil.getToast(this, "没有获取你的权限")
            }
        }
    }

    private fun openAlbum() {
        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.type = "image/*"
        startActivityForResult(intent, CHOOSE_PHOTO)
    }

    private fun handleImageOnKitKat(data: Intent) {
        var imagePath: String? = null
        val uri = data.data
        if (DocumentsContract.isDocumentUri(this, uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            if ("com.android.providers.media.documents" == uri!!.authority) {
                val id = docId.split(":".toRegex()).toTypedArray()[1]
                val selection = MediaStore.Images.Media._ID + "=" + id
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection)
            } else if ("com.android.providers.media.downloads.documents" == uri.authority) {
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"),
                    docId.toLong()
                )
                imagePath = getImagePath(contentUri, null)
            }
        } else if ("content".equals(uri!!.scheme, ignoreCase = true)) {
            imagePath = getImagePath(uri, null)
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            imagePath = uri.path
        }
        displayImage(imagePath)
    }

    private fun displayImage(imagePath: String?) {
        if (imagePath != null) {
            val bitmap = BitmapFactory.decodeFile(imagePath)
            mainViewModel.value.getBitmap(bitmap)
        } else {
            ToastUtil.getToast(this, "无法获取图像")
        }
    }

    @SuppressLint("Range")
    private fun getImagePath(externalContentUri: Uri, selection: String?): String? {
        var path: String? = null
        val cursor = contentResolver.query(
            externalContentUri,
            null, selection, null, null
        )
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            cursor.close()
        }
        return path
    }

}