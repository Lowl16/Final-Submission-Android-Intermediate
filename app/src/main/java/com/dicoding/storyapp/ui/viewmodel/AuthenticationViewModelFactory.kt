package com.dicoding.storyapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.storyapp.di.AuthenticationInjection
import com.dicoding.storyapp.repository.AuthenticationRepository

class AuthenticationViewModelFactory private constructor(private val authenticationRepository: AuthenticationRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthenticationViewModel::class.java)) {
            return AuthenticationViewModel(authenticationRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var INSTANCE: AuthenticationViewModelFactory? = null

        fun getInstance(): AuthenticationViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: AuthenticationViewModelFactory(AuthenticationInjection.provideRepository())
            }.also { INSTANCE = it }
    }
}