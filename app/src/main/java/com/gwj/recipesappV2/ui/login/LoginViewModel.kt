package com.gwj.recipesappV2.ui.login

import androidx.lifecycle.viewModelScope
import com.gwj.recipesappV2.core.service.AuthService
import com.gwj.recipesappV2.data.repo.FavoriteRepo
import com.gwj.recipesappV2.data.repo.UserRepo
import com.gwj.recipesappV2.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authService: AuthService,
) : BaseViewModel() {

    fun login(email: String, pass: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val res = safeApiCall { authService.login(email, pass) }

            if (res == null) {
                _error.emit("Email or password is wrong")
            } else {
                _success.emit("Login successful")
            }
        }
    }
}