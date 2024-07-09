package com.dicoding.storyapp.repository

import com.dicoding.storyapp.data.retrofit.authentication.AuthenticationService

class AuthenticationRepository private constructor(private val authenticationService: AuthenticationService) {

    suspend fun register(name: String, email: String, password: String) = authenticationService.register(name, email, password)

    suspend fun login(email: String, password: String) = authenticationService.login(email, password)

    companion object {
        @Volatile
        private var INSTANCE: AuthenticationRepository? = null

        fun getInstance(
            authenticationService: AuthenticationService,
        ): AuthenticationRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: AuthenticationRepository(authenticationService)
            }.also { INSTANCE = it }
    }
}