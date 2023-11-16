package com.gwj.recipesappV2.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.gwj.recipesappV2.R
import com.gwj.recipesappV2.databinding.FragmentLoginBinding
import com.gwj.recipesappV2.ui.base.BaseFragment
import com.gwj.recipesappV2.ui.base.BaseViewModel
import com.gwj.recipesappV2.ui.login.LoginViewModel
import com.gwj.recipesappV2.ui.starting.StartingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUIComponents() {
        super.setupUIComponents()

        binding.run {
            tvSignUp.setOnClickListener {
                val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
                navController.navigate(action)
            }

            btnSignIn.setOnClickListener {
                val email = etLoginEmail.text.toString()
                val pass = etLoginPassword.text.toString()
                viewModel.login(email, pass)
            }

        }
    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()

        lifecycleScope.launch {
            viewModel.success.collect {
                val action = LoginFragmentDirections.toHome()
                navController.navigate(action)
            }
        }
    }
}