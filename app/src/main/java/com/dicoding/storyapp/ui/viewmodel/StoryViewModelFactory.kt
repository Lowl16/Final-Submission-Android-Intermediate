package com.dicoding.storyapp.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.storyapp.di.StoryInjection
import com.dicoding.storyapp.repository.StoryRepository

class StoryViewModelFactory private constructor(private val storyRepository: StoryRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StoryViewModel::class.java)) {
            return StoryViewModel(storyRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var INSTANCE: StoryViewModelFactory? = null

        fun getInstance(context: Context): StoryViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: StoryViewModelFactory(StoryInjection.provideRepository(context))
            }.also { INSTANCE = it }
    }
}