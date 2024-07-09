package com.dicoding.storyapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.storyapp.data.preferences.UserPreferences

class UserPreferencesViewModelFactory(private val userPreferences: UserPreferences) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserPreferencesViewModel::class.java)) {
            return UserPreferencesViewModel(userPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreferencesViewModelFactory? = null

        fun getInstance(userPreferences: UserPreferences): UserPreferencesViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserPreferencesViewModelFactory(userPreferences)
            }.also { INSTANCE = it }
    }
}