package com.dicoding.storyapp.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.storyapp.di.StoryInjection
import com.dicoding.storyapp.repository.StoryRepository

class MapsViewModelFactory private constructor(private val storyRepository: StoryRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapsViewModel::class.java)) {
            return MapsViewModel(storyRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var INSTANCE: MapsViewModelFactory? = null

        fun getInstance(context: Context): MapsViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: MapsViewModelFactory(StoryInjection.provideRepository(context))
            }.also { INSTANCE = it }
    }
}