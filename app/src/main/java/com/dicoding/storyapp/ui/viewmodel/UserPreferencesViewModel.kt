package com.dicoding.storyapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.storyapp.data.model.UserModel
import com.dicoding.storyapp.data.preferences.UserPreferences
import kotlinx.coroutines.launch

class UserPreferencesViewModel(private val userPreferences: UserPreferences) : ViewModel() {

    fun getSession(): LiveData<UserModel> {
        return userPreferences.getSession().asLiveData()
    }

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            userPreferences.saveSession(user)
        }
    }

    fun removeSession() {
        viewModelScope.launch {
            userPreferences.removeSession()
        }
    }
}