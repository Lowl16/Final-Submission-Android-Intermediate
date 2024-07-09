package com.dicoding.storyapp.data.retrofit.authentication

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthenticationConfig {
    companion object {
        fun getApiService(): AuthenticationService {
            val client: OkHttpClient

            client = OkHttpClient.Builder()
                .build()

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(AuthenticationService::class.java)
        }
    }
}