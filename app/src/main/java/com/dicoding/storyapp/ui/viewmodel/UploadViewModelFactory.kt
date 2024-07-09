package com.dicoding.storyapp.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.storyapp.di.UploadInjection
import com.dicoding.storyapp.repository.UploadRepository

class UploadViewModelFactory private constructor(private val uploadRepository: UploadRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UploadViewModel::class.java)) {
            return UploadViewModel(uploadRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var INSTANCE: UploadViewModelFactory? = null

        fun getInstance(context: Context): UploadViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: UploadViewModelFactory(UploadInjection.provideRepository(context))
            }.also { INSTANCE = it }
    }
}