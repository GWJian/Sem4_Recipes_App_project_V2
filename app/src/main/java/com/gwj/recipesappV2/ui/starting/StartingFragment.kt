package com.gwj.recipesappV2.ui.starting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.gwj.recipesappV2.R
import com.gwj.recipesappV2.databinding.FragmentStartingBinding
import com.gwj.recipesappV2.ui.base.BaseFragment
import com.gwj.recipesappV2.ui.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StartingFragment : BaseFragment<FragmentStartingBinding>() {
    override val viewModel: StartingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun setupUIComponents() {
        super.setupUIComponents()

        binding.run {
            btnStartCooking.setOnClickListener {
                val action = StartingFragmentDirections.actionHomeFragmentToLoginFragment()
                navController.navigate(action)
            }
        }

    }


}