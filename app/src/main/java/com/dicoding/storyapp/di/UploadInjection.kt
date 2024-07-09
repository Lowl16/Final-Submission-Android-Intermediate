package com.dicoding.storyapp.di

import android.content.Context
import com.dicoding.storyapp.data.preferences.UserPreferences
import com.dicoding.storyapp.data.preferences.dataStore
import com.dicoding.storyapp.data.retrofit.story.StoryConfig
import com.dicoding.storyapp.repository.UploadRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object UploadInjection {
    fun provideRepository(context: Context): UploadRepository {
        val preferences = UserPreferences.getInstance(context.dataStore)
        val user = runBlocking { preferences.getSession().first() }
        val storyService = StoryConfig.getApiService(user.token)

        return UploadRepository.getInstance(storyService)
    }
}