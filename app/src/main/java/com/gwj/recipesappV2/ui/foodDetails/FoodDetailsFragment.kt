package com.gwj.recipesappV2.ui.foodDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.gwj.recipesappV2.data.model.FavoriteRecipe
import com.gwj.recipesappV2.data.repo.FavoriteRepoRealTimeImpl
import com.gwj.recipesappV2.databinding.FragmentFoodDetailsBinding
import com.gwj.recipesappV2.ui.adapters.FragmentAdapter
import com.gwj.recipesappV2.ui.base.BaseFragment
import com.gwj.recipesappV2.ui.foodDetails.Ingredents.IngredientsFragment
import com.gwj.recipesappV2.ui.foodDetails.Instructions.InstructionsFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
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

            cbFavorite.setOnClickListener {
                lifecycleScope.launch {
                    //get the current logged in user
                    val user = FirebaseAuth.getInstance().currentUser
                    //get the current user id
                    val userId = user?.uid ?: ""
                    //get the current meal
                    val meal = viewModel.meal.first()
                    // 根据复选框的状态切换餐点的收藏状态
                    viewModel.toggleFavorite(userId, meal, cbFavorite.isChecked)
                }
            }

            goBack.setOnClickListener {
                navController.popBackStack()
            }
        }
    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()

        lifecycleScope.launch {
            viewModel.meal.collect { meal ->
                // Update the UI with the meal data
                binding.tvFoodName.setText(meal?.strMeal)
                binding.tvFoodArea.setText(meal?.strArea)

                Glide.with(binding.root)
                    .load(meal?.strMealThumb)
                    .into(binding.ivFoodImage)

                val user = FirebaseAuth.getInstance().currentUser
                val userId = user?.uid ?: ""
                viewModel.checkIsFavorite(userId, meal?.idMeal ?: "")
            }
        }

        lifecycleScope.launch {
            // 收集来自ViewModel的isFavorite StateFlow
            viewModel.isFavorite.collect { isFavorite ->
                // 根据收藏状态更新复选框的状态
                binding.cbFavorite.isChecked = isFavorite
            }
        }

        lifecycleScope.launch {
            // 收集来自ViewModel的favoriteStatusFlow StateFlow
            viewModel.favoriteStatusFlow.collect { status ->
                // 如果状态不为空，则显示带有状态消息的Snackbar
                if (status.isNotEmpty()) {
                    Snackbar.make(binding.root, status, Snackbar.LENGTH_SHORT).show()
                }
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