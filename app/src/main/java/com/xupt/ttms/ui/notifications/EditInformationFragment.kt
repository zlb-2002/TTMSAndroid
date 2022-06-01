package com.xupt.ttms.ui.notifications

import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.xupt.ttms.R
import com.xupt.ttms.databinding.FragmentDashboardBinding
import com.xupt.ttms.databinding.FragmentEditInformationBinding
import com.xupt.ttms.ui.dashboard.DashboardViewModel
import com.xupt.ttms.ui.login.afterTextChanged
import com.xupt.ttms.util.tool.ToastUtil
import okhttp3.internal.notify
import kotlin.math.max

class EditInformationFragment : Fragment() {

    private var _binding: FragmentEditInformationBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditInformationBinding.inflate(inflater, container, false)

        val notificationsViewModel = ViewModelProvider(this)[NotificationsViewModel::class.java]

        notificationsViewModel.getUserInformation()

        binding.editName.apply {
            text ?: notificationsViewModel.userInformation.value?.data?.username
            afterTextChanged {
                notificationsViewModel.nameChange(it)
                notificationsViewModel.isCommit()
            }
            maxLines = 1

        }
        binding.editAge.apply {
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
            afterTextChanged {
                notificationsViewModel.emailChange(it)
                notificationsViewModel.isCommit()
            }
            maxLines = 1
        }
        binding.editGender.apply {
            afterTextChanged {
                notificationsViewModel.genderChange(it)
                notificationsViewModel.isCommit()
            }
            maxLines = 1
            filters = arrayOf(InputFilter.LengthFilter(1))
        }
        binding.editIntroduce.apply {
            afterTextChanged {
                notificationsViewModel.introduceChange(it)
                notificationsViewModel.isCommit()
            }
        }

        binding.editCommit.apply {
            isEnabled = false
            setOnClickListener {
                notificationsViewModel.postUserInformation()
            }

        }

        notificationsViewModel.commitResult.observe(viewLifecycleOwner) {
            it ?: return@observe
            if (it) findNavController().navigate(R.id.action_editInformationFragment_to_navigation_notifications)
            else ToastUtil.getToast(context, "提交失败，请重试")
        }

        notificationsViewModel.isCommit.observe(viewLifecycleOwner) {
            binding.editCommit.isEnabled = it
        }

        return binding.root
    }

}