package com.xupt.ttms.ui.notifications

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputFilter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import coil.load
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.xupt.ttms.R
import com.xupt.ttms.databinding.FragmentEditInformationBinding
import com.xupt.ttms.ui.login.afterTextChanged
import com.xupt.ttms.util.tool.ToastUtil

class EditInformationFragment : Fragment() {


    private var _binding: FragmentEditInformationBinding? = null

    private val binding get() = _binding!!
    private val CHOOSE_PHOTO: Int = 2
    private val mainViewModel by activityViewModels<EditActivityViewModel>()

    private val REQUEST_IMAGE_CAPTURE = 1
    private lateinit var bottomSheetDialog:BottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditInformationBinding.inflate(inflater, container, false)

        val notificationsViewModel = ViewModelProvider(this)[NotificationsViewModel::class.java]

        notificationsViewModel.getUserInformation()

        notificationsViewModel.userInformation.observe(viewLifecycleOwner) {
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

        mainViewModel.bitmap.observe(viewLifecycleOwner){
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

        notificationsViewModel.commitResult.observe(viewLifecycleOwner) {
            it ?: return@observe
            if (it.information && it.portrait) {
                findNavController().navigate(R.id.action_editInformationFragment_to_navigation_notifications)
                ToastUtil.getToast(context, "修改成功")
            } else if (it.information) {
                ToastUtil.getToast(context,"修改信息失败")
            } else if (it.portrait) {
                ToastUtil.getToast(context, "修改头像失败")
            } else {
                ToastUtil.getToast(context, "修改信息头像失败")
            }
        }

        notificationsViewModel.isCommit.observe(viewLifecycleOwner) {
            binding.editCommit.isEnabled = it
        }

        return binding.root
    }

    private fun dispatchChoosePictureIntent() {
        if (context?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            } !=
            PackageManager.PERMISSION_GRANTED
        ) {
            activity?.let { it1 ->
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
            activity?.packageManager?.let {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}