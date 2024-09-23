package com.example.farmkat.fragments.loginRegister

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.farmkat.R
import com.example.farmkat.data.User
import com.example.farmkat.databinding.FragmentRegisterBinding
import com.example.farmkat.util.RegisterValidation
import com.example.farmkat.util.Resource
import com.example.farmkat.viewmodel.RegisterViewmodel
import com.google.android.material.snackbar.Snackbar
import dagger.BindsInstance
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext

private val TAG = "RegisterFragment"

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<RegisterViewmodel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dohaveacc.setOnClickListener{
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.apply {
            BtnRegReg.setOnClickListener {
                val user = User(
                    RegFirstname.text.toString().trim(),
                    RegLastname.text.toString().trim(),
                    RegEmail.text.toString().trim()
                )
                val passwords = RegPass.text.toString()
                viewModel.createAccountwithEmailAndpassword(user, passwords)
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.register.collect {
                when (it) {
                    is Resource.Loading -> {
                        binding.BtnRegReg.startAnimation()

                    }
                    is Resource.Success -> {
                        Log.d("test", it.data.toString())
                        binding.BtnRegReg.revertAnimation()

                    }
                    is Resource.Error -> {
                        Log.e(TAG, it.message.toString())
                        binding.BtnRegReg.revertAnimation()

                    }
                    else -> Unit
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.validation.collect { validation ->
                if (validation.email is RegisterValidation.Failed) {
                    withContext(Dispatchers.Main) {
                        binding.RegEmail.apply {
                            requestFocus()
                            error = validation.email.message
                        }
                    }
                }
                if (validation.password is RegisterValidation.Failed) {
                    withContext(Dispatchers.Main) {
                        binding.RegPass.apply {
                            requestFocus()
                            error = validation.password.message

                        }
                    }
                }
            }
        }
    }
}