package com.gwj.recipesappV2.ui.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.gwj.recipesappV2.data.model.Category
import com.gwj.recipesappV2.data.model.Meal
import com.gwj.recipesappV2.data.repo.CategoryRepo
import com.gwj.recipesappV2.data.repo.GetAllMealsRepo
import com.gwj.recipesappV2.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val Category: CategoryRepo,
    private val Meals: GetAllMealsRepo
) : BaseViewModel() {
    protected val _categories: MutableStateFlow<List<Category>> = MutableStateFlow(emptyList())
    val categories: StateFlow<List<Category>> = _categories
    protected val _meals: MutableStateFlow<List<Meal>> = MutableStateFlow(emptyList())
    val meals: StateFlow<List<Meal>> = _meals

    private val _selectedCatId = MutableStateFlow("") // we get empty String "", so we can set it selectedCatId into it
    val selectedCatId : StateFlow<String> = _selectedCatId //String becuz we getting one id at a time

    init {
        getAllCategories()
        getAllMeals()
        searchMeals("")
        getMealsByCategory()
    }

    //==================== Get All Categories Start ====================
    private fun getAllCategories() {
        viewModelScope.launch {
            try {
                Category.getAllCategories().let {
                    _categories.value = it
                    //Log.d("debugging_HomeViewModel", "getAllCategories: $it")
                }
            } catch (e: Exception) {
                _error.emit(e.message ?: "Something went wrong")
            }
        }
    }
    //==================== Get All Categories End ====================

    //==================== Get All Meals Start ====================
    private fun getAllMeals() {
        viewModelScope.launch {
            try {
                Meals.getAllMeals().let {
                    _meals.value = it // just display the meals list
                    //Log.d("debugging_HomeViewModel", "getAllMeals: $it")
                }
            } catch (e: Exception) {
                //Log.d("debugging_HomeViewModel", "getAllMeals_error: $e")
                _error.emit(e.message ?: "Something went wrong")
            }
        }
    }
    //==================== Get All Meals End ====================

    //==================== Search Meals Start ====================
    fun searchMeals(query: String?) {
        // Check if the query is not null or blank
        if (!query.isNullOrBlank()) {
            viewModelScope.launch {
                try {
                    val meals = Meals.searchAllMeals(query)// Call the API to get the meals based on the query
                    _meals.value = meals // update the mean list when user search for a meal
                } catch (e: Exception) {
                    //Log.d("debugging_HomeViewModel", "searchMeals_error: $e")
                    _error.emit(e.message ?: "Something went wrong while searching meals")
                }
            }
        }
    }
    //==================== Search Meals End ====================

    //==================== get meals by category Start ====================
    fun getMealsByCategory(category: Category = Category()) //Category = Category() so we can get the all data in the Category model and use in here
    {
        _selectedCatId.value = category.idCategory

        viewModelScope.launch {
            try {
                val meals = Meals.getCategoryMeals(category.strCategory)
                _meals.value = emptyList() // Clear the meals list
                _meals.value = meals // Update the meals list with the new meals
            } catch (e: Exception) {
                //Log.d("debugging_HomeViewModel", "getMealsByCategory_error: $e")
                _error.emit(e.message ?: "Something went wrong while searching meals")
            }
        }
    }
    //==================== get meals by category End ====================

}