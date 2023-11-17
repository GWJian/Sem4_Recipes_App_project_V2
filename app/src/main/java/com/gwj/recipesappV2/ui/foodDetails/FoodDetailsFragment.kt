package com.gwj.recipesappV2.ui.foodDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.gwj.recipesappV2.databinding.FragmentFoodDetailsBinding
import com.gwj.recipesappV2.ui.adapters.FragmentAdapter
import com.gwj.recipesappV2.ui.base.BaseFragment
import com.gwj.recipesappV2.ui.foodDetails.Ingredents.IngredientsFragment
import com.gwj.recipesappV2.ui.foodDetails.Instructions.InstructionsFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FoodDetailsFragment : BaseFragment<FragmentFoodDetailsBinding>() {
    override val viewModel: FoodDetailsViewModel by viewModels()
    val args: FoodDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFoodDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUIComponents() {
        super.setupUIComponents()
        viewModel.getMealByName(args.mealName)

        binding.run {
            goBack.setOnClickListener {
                navController.popBackStack()
            }
        }

    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()

        lifecycleScope.launch {
            viewModel.meal.collect {
                binding.tvFoodName.setText(it?.strMeal)
                binding.tvFoodArea.setText(it?.strArea)

                Glide.with(binding.root)
                    .load(it?.strMealThumb)
                    .into(binding.ivFoodImage)

            }
        }
    }

    //============== View Pager 2 Start ==============//
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vpFoodContainer.adapter = FragmentAdapter(
            this,
            listOf(IngredientsFragment(), InstructionsFragment())
        )

        TabLayoutMediator(binding.tlFoodTabs, binding.vpFoodContainer) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Ingredients"
                }

                else -> {
                    tab.text = "Instructions"
                }
            }
        }.attach()
    }
    //============== View Pager 2 End ==============//
}