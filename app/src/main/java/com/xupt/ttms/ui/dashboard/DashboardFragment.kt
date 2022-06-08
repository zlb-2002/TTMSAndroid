package com.xupt.ttms.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xupt.ttms.databinding.FragmentDashboardBinding
import com.xupt.ttms.util.adapter.OrderAdapter

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    private val binding get() = _binding!!

    private val dashboardViewModel by lazy {
        ViewModelProvider(this)[DashboardViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)


        dashboardViewModel.order.observe(viewLifecycleOwner) {
            if (it.status == 0) {
                binding.orderRecycler.layoutManager = LinearLayoutManager(context)
                binding.orderRecycler.adapter = OrderAdapter(it.data)
            }
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        dashboardViewModel.getOrder()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}