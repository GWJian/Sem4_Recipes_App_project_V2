package com.gwj.recipesappV2.ui.foodDetails.Ingredents

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.gwj.recipesappV2.databinding.FragmentIngredentsBinding
import com.gwj.recipesappV2.ui.adapters.IngredientsAdapter
import com.gwj.recipesappV2.ui.base.BaseFragment
import com.gwj.recipesappV2.ui.foodDetails.FoodDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class IngredientsFragment : BaseFragment<FragmentIngredentsBinding>() {
    private lateinit var ingredientsAdapter: IngredientsAdapter

    override val viewModel: FoodDetailsViewModel by viewModels(
        ownerProducer = { requireParentFragment() } //this will use the parent viewmodel from FoodDetailsViewModel
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIngredentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUIComponents() {
        super.setupUIComponents()

        ingredientsAdapter()
    }

    private fun ingredientsAdapter() {
        ingredientsAdapter = IngredientsAdapter(emptyList())

        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.ingredientsRV.adapter = ingredientsAdapter
        binding.ingredientsRV.layoutManager = linearLayoutManager
    }


    override fun setupViewModelObserver() {
        super.setupViewModelObserver()

        lifecycleScope.launch {
            viewModel.ingredientsWithMeasurements.collect { ingredients ->
                ingredientsAdapter.ingredientData(ingredients)
            }
        }
    }
}