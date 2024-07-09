package com.dicoding.storyapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.storyapp.data.database.StoryEntity
import com.dicoding.storyapp.data.response.Story
import com.dicoding.storyapp.repository.StoryRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class StoryViewModel(private val storyRepository: StoryRepository) : ViewModel() {

    val getStories: LiveData<PagingData<StoryEntity>> = storyRepository.getStories().cachedIn(viewModelScope)

    private val _detailStories = MutableLiveData<Story>()
    val detailStories: LiveData<Story> = _detailStories

    private val _responseCode = MutableLiveData<Int>()

    fun getDetailStories(id: String) {
        viewModelScope.launch {
            try {
                val response = storyRepository.getDetailStories(id)

                if (response.isSuccessful) _detailStories.value = response.body()?.story else _responseCode.value = response.code()
            } catch (e: HttpException) {
                _responseCode.value = e.code()
            }
        }
    }
}