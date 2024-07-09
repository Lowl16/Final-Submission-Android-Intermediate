package com.dicoding.storyapp.di

import android.content.Context
import com.dicoding.storyapp.data.database.StoryDatabase
import com.dicoding.storyapp.data.preferences.UserPreferences
import com.dicoding.storyapp.data.preferences.dataStore
import com.dicoding.storyapp.data.retrofit.story.StoryConfig
import com.dicoding.storyapp.repository.StoryRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object StoryInjection {
    fun provideRepository(context: Context): StoryRepository {
        val preferences = UserPreferences.getInstance(context.dataStore)
        val user = runBlocking { preferences.getSession().first() }
        val database = StoryDatabase.getDatabase(context)
        val storyService = StoryConfig.getApiService(user.token)

        return StoryRepository.getInstance(database, storyService)
    }
}