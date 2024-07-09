package com.dicoding.storyapp.repository

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.dicoding.storyapp.data.database.StoryDatabase
import com.dicoding.storyapp.data.database.StoryEntity
import com.dicoding.storyapp.data.remotemediator.StoryRemoteMediator
import com.dicoding.storyapp.data.retrofit.story.StoryService

class StoryRepository private constructor(private val storyDatabase: StoryDatabase, private val storyService: StoryService) {

    fun getStories(): LiveData<PagingData<StoryEntity>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 2
            ),
            remoteMediator = StoryRemoteMediator(storyDatabase, storyService),
            pagingSourceFactory = {
                storyDatabase.storyDao().getAllStory()
            }
        ).liveData
    }

    suspend fun getStoriesWithLocation(location: Int) = storyService.getStoriesWithLocation(location)

    suspend fun getDetailStories(id: String) = storyService.getDetailStories(id)

    companion object {
        @Volatile
        private var INSTANCE: StoryRepository? = null

        fun getInstance(
            storyDatabase: StoryDatabase,
            storyService: StoryService
        ): StoryRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: StoryRepository(storyDatabase, storyService)
            }.also { INSTANCE = it }
    }
}