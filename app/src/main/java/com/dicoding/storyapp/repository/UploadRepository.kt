package com.dicoding.storyapp.repository

import androidx.lifecycle.liveData
import com.dicoding.storyapp.data.response.StoryUploadResponse
import com.dicoding.storyapp.data.retrofit.story.StoryService
import com.dicoding.storyapp.data.state.ResultState
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File

class UploadRepository private constructor(private val storyService: StoryService) {

    fun uploadStories(file: File, description: String, lat: Double?, lon: Double?) = liveData {
        emit(ResultState.Loading)

        val requestBody = description.toRequestBody("text/plain".toMediaType())
        val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "photo",
            file.name,
            requestImageFile
        )

        try {
            val successResponse = storyService.uploadStories(multipartBody, requestBody, lat, lon)

            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, StoryUploadResponse::class.java)

            emit(ResultState.Error(errorResponse.message))
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UploadRepository? = null

        fun getInstance(
            storyService: StoryService
        ): UploadRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: UploadRepository(storyService)
            }.also { INSTANCE = it }
    }
}