package com.example.farmkat.fragments.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.farmkat.R
import com.example.farmkat.adapters.HomeViewpagerAdapter
import com.example.farmkat.databinding.FragmentHomeBinding
import com.example.farmkat.fragments.categories.*
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoriesFragments = arrayListOf<Fragment>(
            MainCategoryFragment(),
            FruitsFragment(),
            VegetablesFragment(),
            CropsFragment(),
            EquipmentFragment(),
            AgrochemicalsFragment()
        )

        binding.viewPagerHome.isUserInputEnabled = false // to cancel the swipe behaviour in app

        val viewPager2Adapter =
            HomeViewpagerAdapter(categoriesFragments, childFragmentManager, lifecycle)
        binding.viewPagerHome.adapter = viewPager2Adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPagerHome) { tab, position ->
            when (position) {
                0 -> tab.text = "Main"
                1 -> tab.text = "Fruits"
                2 -> tab.text = "Vegetables"
                3 -> tab.text = "Crops"
                4 -> tab.text = "Equipment"
                5 -> tab.text = "Agrochemicals"
            }
        }.attach()
    }
}