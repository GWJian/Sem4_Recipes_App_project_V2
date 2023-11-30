package com.gwj.recipesappV2.ui.foodDetails

import android.util.Log
import android.util.LogPrinter
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.FirebaseDatabase
import com.gwj.recipesappV2.data.model.FavoriteRecipe
import com.gwj.recipesappV2.data.model.Meal
import com.gwj.recipesappV2.data.model.User
import com.gwj.recipesappV2.data.repo.FavoriteRepo
import com.gwj.recipesappV2.data.repo.FavoriteRepoRealTimeImpl
import com.gwj.recipesappV2.data.repo.GetAllMealsRepo
import com.gwj.recipesappV2.data.repo.UserRepo
import com.gwj.recipesappV2.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
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

    private val _strYoutube: MutableStateFlow<String> = MutableStateFlow("")
    val strYoutube: StateFlow<String> = _strYoutube

    private val _favoriteStatus = MutableStateFlow<String>("")
    val favoriteStatusFlow: StateFlow<String> = _favoriteStatus

    private val _isFavorite = MutableStateFlow<Boolean>(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    fun getMealByName(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Meals.getMealByName(name).let {
                    _meal.value = it
                    _instructions.value = it?.strInstructions ?: ""
                    _strYoutube.value = it?.strYoutube ?: ""
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

    suspend fun toggleFavorite(userId: String, meal: Meal?, isChecked: Boolean) {
        // 获取到Firebase实时数据库中"favorites"节点/Get a reference to the "favorites" node in the Firebase Realtime Database
        val dbRef = FirebaseDatabase.getInstance().getReference("favorites")
        // 创建一个FavoriteRepoRealTimeImpl对象/Create a new instance of the FavoriteRepoRealTimeImpl class
        val repo = FavoriteRepoRealTimeImpl(dbRef)

        if (isChecked) {
            // if the checkbox is checked, add the recipe to the favorites
            val favoriteRecipe = FavoriteRecipe(
                idMeal = meal?.idMeal ?: "",
                strMeal = meal?.strMeal ?: "",
                id = meal?.idMeal ?: "",
                strMealThumb = meal?.strMealThumb ?: "",
            )
            // 尝试将菜谱添加到数据库的收藏夹中，并获取结果/Try to add the recipe to the favorites in the database and get the result
            val result = repo.AddToFavorite(userId, favoriteRecipe).first()
            _favoriteStatus.value =
                if (result) "Added to favorites" else "Failed to add to favorites"
        } else {
            repo.RemoveFromFavorite(userId, meal?.idMeal ?: "")
            _favoriteStatus.value = "Removed from favorites"
        }
    }

    suspend fun checkIsFavorite(userId: String, idMeal: String?) {
        // 获取到Firebase实时数据库中"favorites"节点/Get a reference to the "favorites" node in the Firebase Realtime Database
        val dbRef = FirebaseDatabase.getInstance().getReference("favorites")
        // 创建一个FavoriteRepoRealTimeImpl对象/Create a new instance of the FavoriteRepoRealTimeImpl class
        val repo = FavoriteRepoRealTimeImpl(dbRef)
        // 检查菜谱是否在数据库中被标记为收藏/Check if the recipe is marked as favorite in the database
        val isFavorite = repo.isFavorite(userId, idMeal ?: "")
        // if the recipe is marked as favorite, checkbox is checked
        _isFavorite.value = isFavorite
    }
}
