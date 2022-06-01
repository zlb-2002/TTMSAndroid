package com.xupt.ttms.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import coil.load
import com.xupt.ttms.R
import com.xupt.ttms.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    private val binding get() = _binding!!
    private lateinit var notificationsViewModel:NotificationsViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        notificationsViewModel = ViewModelProvider(this)[NotificationsViewModel::class.java]
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)

        notificationsViewModel.userInformation.observe(viewLifecycleOwner){
            Log.d("TAG", "onCreateView: $it")
            binding.myNavigation.menu.apply {
                getItem(0).title = "手机号 : ${it?.data?.phone}"
                getItem(1).title = "年龄 : ${it?.data?.age}"
                when (it?.data?.gender) {
                    "0" -> getItem(2).title = "性别 : 男"
                    "1" -> getItem(2).title = "性别 : 女"

                }
                getItem(3).title = "邮箱 :${it?.data?.email}"
                getItem(4).title = "介绍 :${it?.data?.introduce}"
                getItem(5).title = "余额 :${it?.data?.balance}"
            }
            binding.userPortrait.load(it?.data?.portrait) {
                error(R.drawable.user)
            }

            binding.userName.text = it?.data?.username
        }

        binding.editButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_notifications_to_editInformationFragment)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        notificationsViewModel.getUserInformation()
    }
}