package com.gwj.recipesappV2.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.gwj.recipesappV2.R
import com.gwj.recipesappV2.databinding.FragmentRegisterBinding
import com.gwj.recipesappV2.ui.base.BaseFragment
import com.gwj.recipesappV2.ui.base.BaseViewModel
import com.gwj.recipesappV2.ui.register.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {
    override val viewModel: RegisterViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUIComponents() {
        super.setupUIComponents()

        binding.run {

            btnSignUp.setOnClickListener {
                val name = etName.text.toString()
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                val confirmPassword = etConfirmPassword.text.toString()
                viewModel.register(name,email, password, confirmPassword)
            }

            tvSignIn.setOnClickListener {
                navController.popBackStack()
            }
        }
    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()

        lifecycleScope.launch {
            viewModel.success.collect {
                val action = RegisterFragmentDirections.toLogin()
                navController.navigate(action)
            }
        }
    }


}