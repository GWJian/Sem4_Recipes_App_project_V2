package com.gwj.recipesappV2.ui.profile

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.gwj.recipesappV2.core.service.AuthService
import com.gwj.recipesappV2.core.service.StorageService
import com.gwj.recipesappV2.data.model.favouriteRecipe
import com.gwj.recipesappV2.data.model.User
import com.gwj.recipesappV2.data.repo.favouriteRepo
import com.gwj.recipesappV2.data.repo.UserRepo
import com.gwj.recipesappV2.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authService: AuthService,
    private val storageService: StorageService,
    private val userRepo: UserRepo,
    private val favouriteRepo: favouriteRepo
) : BaseViewModel() {
    private val _user = MutableStateFlow(User(name = "Unknown", email = "Unknown"))
    val user: StateFlow<User> = _user

    private val _profileUri = MutableStateFlow<Uri?>(null)
    val profileUri: StateFlow<Uri?> = _profileUri

    private val _finish = MutableSharedFlow<Unit>()
    val finish: SharedFlow<Unit> = _finish

    private val _favourite: MutableStateFlow<List<favouriteRecipe>> = MutableStateFlow(emptyList())
    val favourite: StateFlow<List<favouriteRecipe>> = _favourite

    init {
        getCurrentUser()
        getProfilePicUri()
        getAllFavourite()
    }

    fun logout() {
        authService.logout()
        viewModelScope.launch(Dispatchers.IO) {
            _finish.emit(Unit)
        }
    }

    // Update profile picture
    fun updateProfilePic(uri: Uri) {
        user.value.id?.let {
            viewModelScope.launch(Dispatchers.IO) {
                val name = "${it}.jpg"
                storageService.addImage(name, uri)
                getProfilePicUri()
            }
        }
    }

    // Fetch profile picture URI
    fun getProfilePicUri() {
        viewModelScope.launch(Dispatchers.IO) {
            authService.getCurrentUser()?.uid.let {
                _profileUri.value = storageService.getImage("${it}.jpg")
            }
        }
    }

    // Fetch current user
    fun getCurrentUser() {
        val firebaseUser = authService.getCurrentUser()
        firebaseUser?.let {
            viewModelScope.launch(Dispatchers.IO) {
                safeApiCall { userRepo.getUser(it.uid) }?.let { user ->
                    _user.value = user
                }
            }
        }
    }

    // Fetch all favourite recipes
    fun getAllFavourite() {
        authService.getCurrentUser()?.uid?.let { id ->
            viewModelScope.launch(Dispatchers.IO) {
                safeApiCall {
                    favouriteRepo.getAllfavouriteRecipe(id)
                }?.collect() {
                    _favourite.value = it
                }
            }
        }
    }
}