package com.gwj.recipesappV2.ui.foodDetails

import android.util.Log
import android.util.LogPrinter
import androidx.lifecycle.viewModelScope
import com.gwj.recipesappV2.data.model.FavoriteRecipe
import com.gwj.recipesappV2.data.model.Meal
import com.gwj.recipesappV2.data.model.User
import com.gwj.recipesappV2.data.repo.FavoriteRepo
import com.gwj.recipesappV2.data.repo.GetAllMealsRepo
import com.gwj.recipesappV2.data.repo.UserRepo
import com.gwj.recipesappV2.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodDetailsViewModel @Inject constructor(
    private val Meals: GetAllMealsRepo,
) : BaseViewModel() {
    private val _meal: MutableStateFlow<Meal?> = MutableStateFlow(null)
    val meal: StateFlow<Meal?> = _meal

    private val _ingredientsWithMeasurements: MutableStateFlow<List<Pair<String, String>>> =
        MutableStateFlow(emptyList())
    val ingredientsWithMeasurements: StateFlow<List<Pair<String, String>>> =
        _ingredientsWithMeasurements

    private val _instructions: MutableStateFlow<String> = MutableStateFlow("")
    val instructions: StateFlow<String> = _instructions


    fun getMealByName(name: String) {
        viewModelScope.launch {
            try {
                Meals.getMealByName(name).let {
                    _meal.value = it
                    _instructions.value = it?.strInstructions ?: ""
                    _ingredientsWithMeasurements.value = listOf(
                        Pair(it?.strIngredient1 ?: "", it?.strMeasure1 ?: ""),
                        Pair(it?.strIngredient2 ?: "", it?.strMeasure2 ?: ""),
                        Pair(it?.strIngredient3 ?: "", it?.strMeasure3 ?: ""),
                        Pair(it?.strIngredient4 ?: "", it?.strMeasure4 ?: ""),
                        Pair(it?.strIngredient5 ?: "", it?.strMeasure5 ?: ""),
                        Pair(it?.strIngredient6 ?: "", it?.strMeasure6 ?: ""),
                        Pair(it?.strIngredient7 ?: "", it?.strMeasure7 ?: ""),
                        Pair(it?.strIngredient8 ?: "", it?.strMeasure8 ?: ""),
                        Pair(it?.strIngredient9 ?: "", it?.strMeasure9 ?: ""),
                        Pair(it?.strIngredient10 ?: "", it?.strMeasure10 ?: ""),
                        Pair(it?.strIngredient11 ?: "", it?.strMeasure11 ?: ""),
                        Pair(it?.strIngredient12 ?: "", it?.strMeasure12 ?: ""),
                        Pair(it?.strIngredient13 ?: "", it?.strMeasure13 ?: ""),
                        Pair(it?.strIngredient14 ?: "", it?.strMeasure14 ?: ""),
                        Pair(it?.strIngredient15 ?: "", it?.strMeasure15 ?: ""),
                        Pair(it?.strIngredient16 ?: "", it?.strMeasure16 ?: ""),
                        Pair(it?.strIngredient17 ?: "", it?.strMeasure17 ?: ""),
                        Pair(it?.strIngredient18 ?: "", it?.strMeasure18 ?: ""),
                        Pair(it?.strIngredient19 ?: "", it?.strMeasure19 ?: ""),
                        Pair(it?.strIngredient20 ?: "", it?.strMeasure20 ?: ""),
                    ).filter {
                        it.first.isNotEmpty() && it.second.isNotEmpty()
                    }
                }
            } catch (e: Exception) {
                //Log.d("debugging_FoodDetailsViewModel", "getMealByName_error: $e")
                //throw e
                _error.emit(e.message ?: "Meals Something went wrong")
            }
        }
    }
}


//private val _ingredients: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
//val ingredients: StateFlow<List<String>> = _ingredients
//
//private val _image: MutableStateFlow<String?> = MutableStateFlow(null)
//val image: StateFlow<String?> = _image
//
//private val _measurements: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
//val measurements: StateFlow<List<String>> = _measurements


//fun getMealByName(name: String) {
//    viewModelScope.launch {
//        try {
//            Meals.getMealByName(name).let {
//                _meal.value = it
//                _image.value = it?.strMealThumb
//                _ingredients.value = listOf(
//                    it?.strIngredient1,
//                    it?.strIngredient2,
//                    it?.strIngredient3,
//                    it?.strIngredient4,
//                    it?.strIngredient5,
//                    it?.strIngredient6,
//                    it?.strIngredient7,
//                    it?.strIngredient8,
//                    it?.strIngredient9,
//                    it?.strIngredient10,
//                    it?.strIngredient11,
//                    it?.strIngredient12,
//                    it?.strIngredient13,
//                    it?.strIngredient14,
//                    it?.strIngredient15,
//                    it?.strIngredient16,
//                    it?.strIngredient17,
//                    it?.strIngredient18,
//                    it?.strIngredient19,
//                    it?.strIngredient20,
//                ).filter { it.isNotEmpty() }
//            }
//        } catch (e: Exception) {
//            //Log.d("debugging_FoodDetailsViewModel", "getMealByName_error: $e")
//            //throw e
//            _error.emit(e.message ?: "Meals Something went wrong")
//        }
//    }
//}
//}
