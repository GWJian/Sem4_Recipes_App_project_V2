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
                    //get current logged in user
                    val user = FirebaseAuth.getInstance().currentUser
                    //Get the user ID of the current user
                    val userId = user?.uid ?: ""
                    //TODO ASK SIR WHY FIRST()?
                    val meal = viewModel.meal.first()

                    // 获取到Firebase实时数据库中"favorites"节点的引用
                    val dbRef = FirebaseDatabase.getInstance().getReference("favorites")

                    if (cbFavorite.isChecked) {
                        // 创建一个FavoriteRecipe对象
                        val favoriteRecipe = FavoriteRecipe(
                            idMeal = meal?.idMeal ?: "",
                            strMeal = meal?.strMeal ?: "",
                            id = meal?.idMeal ?: ""
                        )
                        // 创建一个FavoriteRepoRealTimeImpl对象
                        val repo = FavoriteRepoRealTimeImpl(dbRef)
                        // 将收藏的菜谱添加到数据库并获取结果
                        val result = repo.AddToFavorite(userId, favoriteRecipe).first()
                        // 如果结果为true，显示一个Snackbar消息"Added to favorites"，否则显示"Failed to add to favorites"
                        if (result) {
                            Snackbar.make(
                                binding.root,
                                "Added to favorites",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        } else {
                            Snackbar.make(
                                binding.root,
                                "Failed to add to favorites",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        val repo = FavoriteRepoRealTimeImpl(dbRef)
                        // 从数据库的收藏夹中删除
                        repo.RemoveFromFavorite(userId, meal?.idMeal ?: "")
                    }
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

                // Get the current logged-in user
                val user = FirebaseAuth.getInstance().currentUser
                // Get the user ID of the current user
                val userId = user?.uid ?: ""

                // Get a DatabaseReference to the favorites node in Firebase Realtime Database
                val dbRef = FirebaseDatabase.getInstance().getReference("favorites")
                val repo = FavoriteRepoRealTimeImpl(dbRef)

                // Check if the meal is already marked as favorite
                val isFavorite = repo.isFavorite(userId, meal?.idMeal ?: "")
                // Set the checkbox state based on whether the meal is marked as favorite
                binding.cbFavorite.isChecked = isFavorite
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