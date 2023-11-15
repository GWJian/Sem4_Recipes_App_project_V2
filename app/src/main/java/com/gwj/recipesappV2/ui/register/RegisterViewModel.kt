package com.gwj.recipesappV2.ui.register

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.gwj.recipesappV2.core.service.AuthService
import com.gwj.recipesappV2.data.repo.UserRepo
import com.gwj.recipesappV2.data.model.User
import com.gwj.recipesappV2.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authService : AuthService,
    private val userRepo: UserRepo
) : BaseViewModel() {

    fun register(email: String, pass: String, confirmPass: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val error = validate(email, pass, confirmPass)

            if (!error.isNullOrEmpty()) {
                _error.emit(error)
            } else {
                val res = safeApiCall { authService.register(email, pass) }
                if (res == null) {
                    _error.emit("Could not create account")
                } else {
                    userRepo.addUser(
                        res.uid,
                        User(name = "WJ", email = res.email ?: "")
                    )
                    _success.emit("Account created successfully")
                }
            }
        }
    }

    fun validate(email: String, pass: String, confirmPass: String): String? {
        return if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            "Invalid email format"
        } else if (pass.length <= 5) {
            "Password must more than 5"
        } else if (pass != confirmPass) {
            "Password not match"
        } else {
            null
        }
    }
}