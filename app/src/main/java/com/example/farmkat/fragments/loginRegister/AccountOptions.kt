package com.example.farmkat.fragments.loginRegister

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.farmkat.R
import com.example.farmkat.databinding.FragmentAccountOptionsBinding
import com.example.farmkat.databinding.FragmentIntroductionBinding

class AccountOptions : Fragment(R.layout.fragment_account_options){
    private lateinit var  binding: FragmentAccountOptionsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountOptionsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginBtn.setOnClickListener{
            findNavController().navigate(R.id.action_accountOptions_to_loginFragment)
        }
        binding.RegisterBtn.setOnClickListener{
            findNavController().navigate(R.id.action_accountOptions_to_registerFragment)
        }

    }
}