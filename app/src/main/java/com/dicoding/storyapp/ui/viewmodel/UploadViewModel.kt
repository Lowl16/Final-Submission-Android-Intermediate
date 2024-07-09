package com.dicoding.storyapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.dicoding.storyapp.repository.UploadRepository
import java.io.File

class UploadViewModel(private val uploadRepository: UploadRepository) : ViewModel() {

    fun uploadStories(file: File, description: String, lat: Double?, lon: Double?) = uploadRepository.uploadStories(file, description, lat, lon)
}