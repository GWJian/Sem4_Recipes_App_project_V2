package com.gwj.recipesappV2.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.gwj.recipesappV2.data.model.Meal
import com.gwj.recipesappV2.databinding.FragmentHomeBinding
import com.gwj.recipesappV2.ui.adapters.HorizontalCategoryAdapter
import com.gwj.recipesappV2.ui.adapters.IngredientsAdapter
import com.gwj.recipesappV2.ui.adapters.MealAdapter
import com.gwj.recipesappV2.ui.base.BaseFragment
import com.gwj.recipesappV2.ui.profile.ProfileViewModel
import com.gwj.recipesappV2.ui.tabContainer.TabContainerFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val viewModel: HomeViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()
    private lateinit var adapter: HorizontalCategoryAdapter
    private lateinit var adapter2: MealAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUIComponents() {
        super.setupUIComponents()
        setupAdapter2()
        setupAdapter()
    }

    //===================== HorizontalCategoryAdapter Start =====================
    private fun setupAdapter() {
        adapter = HorizontalCategoryAdapter(emptyList()) {
            //this getMealsByCategory is from HomeViewModel,so when we click the category, it will pass the category name to HomeViewModel
            viewModel.getMealsByCategory(it)
        }

        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.horizontalCategoriesRecyclerView.adapter = adapter
        binding.horizontalCategoriesRecyclerView.layoutManager = layoutManager

        lifecycleScope.launch {
            viewModel.meals.collect {
                adapter2.setMeals(it)
            }
        }
    }
    //===================== HorizontalCategoryAdapter End =====================

    //===================== MealAdapter Start =====================
    private fun setupAdapter2() {
        adapter2 = MealAdapter(emptyList())
        //=========================== onClick Start ================================
        adapter2.listener = object : MealAdapter.Listener {
            override fun onClick(meal: Meal) {
                val action =
                    TabContainerFragmentDirections.actionTabContainerToFoodDetails(meal.strMeal)
                navController.navigate(action)
            }
        }
        //=========================== onClick Start ================================

        val staggeredLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.mealsRecyclerView.adapter = adapter2
        binding.mealsRecyclerView.layoutManager = staggeredLayoutManager

        // ======================== Initialize SearchView Start ====================================
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Call searchMeals when the user submits the search query
                viewModel.searchMeals(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Call searchMeals as the user types in the search view
                viewModel.searchMeals(newText)
                return true
            }
        })
        // ======================== Initialize SearchView End ====================================

    }
    //===================== MealAdapter End =====================

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()
        //===================== lifecycleScope error Start =====================
        lifecycleScope.launch {
            viewModel.error.collect() {
                //Log.d("debugging_HomeFragment", "viewModel_error: $it ")
            }
        }
        //===================== lifecycleScope error End =====================

        //===================== lifecycleScope ShowUserName Start =====================
        lifecycleScope.launch {
            profileViewModel.user.collect {
                binding.tvHello.text = "Hello ${it.name}"
            }
        }
        //===================== lifecycleScope ShowUserName End =====================

        //===================== lifecycleScope HorizontalCategoryAdapter Start =====================
        lifecycleScope.launch {
            viewModel.categories.collect {
                adapter.setCategories(it)
                //Log.d("debugging_HomeFragment", "viewModel_categories: $it ")
            }
        }
        //===================== lifecycleScope HorizontalCategoryAdapter End =====================

        //===================== lifecycleScope MealAdapter Start =====================
        lifecycleScope.launch {
//            viewModel.meals.collect {
//                adapter2.setMeals(it)
//                //Log.d("debugging_HomeFragment", "viewModel_meals: $it ")
//            }

            viewModel.isLoading.collect{
                if (it){
                    binding.progressBar.visibility = View.VISIBLE
                } else{
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
        //===================== lifecycleScope MealAdapter End =====================

        //====================== setSelectedCategory Start =================
        lifecycleScope.launch {
            viewModel.selectedCatId.collect{
                adapter.setSelectedCategory(it)
            }
        }
        //====================== setSelectedCategory End =================

    }
}











