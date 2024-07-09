package com.dicoding.storyapp.di

import com.dicoding.storyapp.data.retrofit.authentication.AuthenticationConfig
import com.dicoding.storyapp.repository.AuthenticationRepository

object AuthenticationInjection {
    fun provideRepository(): AuthenticationRepository {
        val apiService = AuthenticationConfig.getApiService()

        return AuthenticationRepository.getInstance(apiService)
    }
}