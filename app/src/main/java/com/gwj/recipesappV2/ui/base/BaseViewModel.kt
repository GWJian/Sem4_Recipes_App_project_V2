package com.gwj.recipesappV2.ui.base

import android.view.View
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

abstract class BaseViewModel:ViewModel() {
    protected val _error:MutableSharedFlow<String> = MutableSharedFlow()
    val error:SharedFlow<String> = _error

    protected val _success:MutableSharedFlow<String> = MutableSharedFlow()
    val success: SharedFlow<String> = _success

    protected val _isLoading: MutableSharedFlow<Boolean>  = MutableSharedFlow()
    val isLoading: SharedFlow<Boolean> = _isLoading
    open fun onCreate(){}

    suspend fun <T> safeApiCall(callback: suspend () -> T?): T? {
        return try {
            callback()
        } catch (e: Exception) {
            _error.emit(e.message ?: "Something went wrong")
            null
        }
    }
}