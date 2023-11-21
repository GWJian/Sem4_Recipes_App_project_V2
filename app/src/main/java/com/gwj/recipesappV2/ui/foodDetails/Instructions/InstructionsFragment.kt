package com.gwj.recipesappV2.ui.foodDetails.Instructions

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gwj.recipesappV2.R
import com.gwj.recipesappV2.databinding.FragmentIngredentsBinding
import com.gwj.recipesappV2.databinding.FragmentInstructionsBinding
import com.gwj.recipesappV2.ui.base.BaseFragment
import com.gwj.recipesappV2.ui.foodDetails.FoodDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InstructionsFragment : BaseFragment<FragmentInstructionsBinding>() {
    override val viewModel: FoodDetailsViewModel by viewModels(
        ownerProducer = { requireParentFragment() } //this will use the parent viewmodel from FoodDetailsViewModel
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInstructionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUIComponents() {
        super.setupUIComponents()
        setupViewModelObserver()
    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()
        lifecycleScope.launch {
            viewModel.instructions.collect { instructions ->
                binding.instructions.text = instructions
                    .replace(Regex("\r"), "\n")
            }

        }
    }
}