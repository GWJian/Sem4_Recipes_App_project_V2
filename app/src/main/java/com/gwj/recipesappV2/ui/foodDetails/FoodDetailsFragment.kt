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
                    val user = FirebaseAuth.getInstance().currentUser
                    val userId = user?.uid ?: ""
                    val meal = viewModel.meal.first()

                    // Get a DatabaseReference to the favorites node
                    val dbRef = FirebaseDatabase.getInstance().getReference("favorites")

                    if (cbFavorite.isChecked) {
                        val favoriteRecipe = FavoriteRecipe(
                            idMeal = meal?.idMeal ?: "",
                            strMeal = meal?.strMeal ?: "",
                            id = meal?.idMeal ?: ""
                        )
                        val repo = FavoriteRepoRealTimeImpl(dbRef)
                        val result = repo.AddToFavorite(userId, favoriteRecipe).first()
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
                        repo.RemoveFromFavorite(userId, meal?.idMeal ?: "")
                        // Removed from favorites
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
                binding.tvFoodName.setText(meal?.strMeal)
                binding.tvFoodArea.setText(meal?.strArea)

                Glide.with(binding.root)
                    .load(meal?.strMealThumb)
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