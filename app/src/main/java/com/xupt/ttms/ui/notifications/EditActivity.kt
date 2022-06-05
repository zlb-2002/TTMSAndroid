package com.xupt.ttms.ui.notifications

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.text.InputFilter
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.xupt.ttms.R
import com.xupt.ttms.databinding.FragmentEditInformationBinding
import com.xupt.ttms.ui.login.afterTextChanged
import com.xupt.ttms.util.tool.ToastUtil

class EditActivity : AppCompatActivity() {

    private lateinit var binding:FragmentEditInformationBinding
    private val CHOOSE_PHOTO: Int = 2
    private val mainViewModel:EditActivityViewModel by lazy { ViewModelProvider(this)[EditActivityViewModel::class.java] }

    private val REQUEST_IMAGE_CAPTURE = 1
    private lateinit var bottomSheetDialog:BottomSheetDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        binding = FragmentEditInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val notificationsViewModel = ViewModelProvider(this)[NotificationsViewModel::class.java]

        notificationsViewModel.getUserInformation()

        notificationsViewModel.userInformation.observe(this) {
            binding.editName.setText(it?.data?.username)
            binding.editName.setText(it?.data?.age.toString())
            binding.editName.setText(it?.data?.email)
            binding.editName.setText(it?.data?.introduce)
            when (it?.data?.gender) {
                "0" -> binding.editGender.setText("男")
                "1" -> binding.editGender.setText("女")
            }
        }

        binding.userPortrait.apply {
            load(notificationsViewModel.userInformation.value?.data?.portrait) {
                error(R.drawable.user)
            }
            setOnClickListener {
                bottomSheetDialog = BottomSheetDialog(context, R.style.BottomSheetDialog)
                bottomSheetDialog.setCancelable(true)
                bottomSheetDialog.setContentView(R.layout.layout_bottomsheetdialog_select_photo)
                bottomSheetDialog.show()

                bottomSheetDialog.window?.findViewById<ImageView>(R.id.image_arrows_select_photo).apply {
                    setOnClickListener {
                        bottomSheetDialog.cancel()
                    }
                }

                bottomSheetDialog.window?.findViewById<TextView>(R.id.take_photo).apply {
                    setOnClickListener {
                        Log.d("TAG", "onCreateView: ")
                        dispatchTakePictureIntent()
                        bottomSheetDialog.cancel()
                    }
                }
                bottomSheetDialog.window?.findViewById<TextView>(R.id.select_photo).apply {
                    setOnClickListener {
                        dispatchChoosePictureIntent()
                        bottomSheetDialog.cancel()
                    }
                }
                bottomSheetDialog.window?.findViewById<TextView>(R.id.select_photo_cancel).apply {
                    setOnClickListener {
                        bottomSheetDialog.cancel()
                    }
                }
            }
        }

        mainViewModel.bitmap.observe(this){
            binding.userPortrait.load(it)
        }

        binding.editName.apply {
            Log.d("TAG", "onCreateView: ${notificationsViewModel.userInformation.value?.data?.username}")
            setText(notificationsViewModel.userInformation.value?.data?.username)
            afterTextChanged {
                notificationsViewModel.nameChange(it)
                notificationsViewModel.isCommit()
            }
            maxLines = 1
        }
        binding.editAge.apply {
            if (notificationsViewModel.userInformation.value?.data?.age != null) {
                setText("${notificationsViewModel.userInformation.value?.data?.age}")
            }

            afterTextChanged {
                if (it != "") {
                    notificationsViewModel.ageChange(Integer.parseInt(it))
                }
                notificationsViewModel.isCommit()
            }
            maxLines = 1
            filters = arrayOf(InputFilter.LengthFilter(3))
        }
        binding.editEmail.apply {
            setText(notificationsViewModel.userInformation.value?.data?.email)
            afterTextChanged {
                notificationsViewModel.emailChange(it)
                notificationsViewModel.isCommit()
            }
            maxLines = 1
        }
        binding.editGender.apply {
            when (notificationsViewModel.userInformation.value?.data?.gender) {
                "0" -> setText("男")
                "1" -> setText("女")
            }
            afterTextChanged {
                notificationsViewModel.genderChange(it)
                notificationsViewModel.isCommit()
            }
            maxLines = 1
            filters = arrayOf(InputFilter.LengthFilter(1))
        }
        binding.editIntroduce.apply {
            setText(notificationsViewModel.userInformation.value?.data?.introduce)
            afterTextChanged {
                notificationsViewModel.introduceChange(it)
                notificationsViewModel.isCommit()
            }
        }

        binding.editCommit.apply {
            isEnabled = false
            setOnClickListener {
                notificationsViewModel.apply {
                    userInformation.value?.data?.let { it1 ->
                        notificationsViewModel.postUserInformation(it1)
                    }
                    binding.userPortrait.isDrawingCacheEnabled = true
                    notificationsViewModel.postUserPortrait(binding.userPortrait.drawingCache)
                    binding.userPortrait.isDrawingCacheEnabled = false
                }
            }
        }

        notificationsViewModel.commitResult.observe(this) {
            it ?: return@observe
            if (it.information && it.portrait) {
                finish()
                ToastUtil.getToast(this, "修改成功")
            } else if (it.information) {
                ToastUtil.getToast(this,"修改信息失败")
            } else if (it.portrait) {
                ToastUtil.getToast(this, "修改头像失败")
            } else {
                ToastUtil.getToast(this, "修改信息头像失败")
            }
        }

        notificationsViewModel.isCommit.observe(this) {
            binding.editCommit.isEnabled = it
        }

    }

    private fun dispatchChoosePictureIntent() {
        if (this.let {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            } !=
            PackageManager.PERMISSION_GRANTED
        ) {
            this.let { it1 ->
                ActivityCompat.requestPermissions(
                    it1,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1
                )
            }
        } else {
            openAlbum()
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            this.packageManager?.let {
                takePictureIntent.resolveActivity(it)?.also {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    private fun openAlbum() {
        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.type = "image/*"
        startActivityForResult(intent, CHOOSE_PHOTO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            mainViewModel.getBitmap(data?.extras?.get("data") as Bitmap)
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
            mainViewModel.getBitmap(bitmap)
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