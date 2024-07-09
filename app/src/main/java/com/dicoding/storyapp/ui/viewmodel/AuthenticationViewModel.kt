package com.dicoding.storyapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.storyapp.data.model.UserModel
import com.dicoding.storyapp.data.response.LoginResponse
import com.dicoding.storyapp.data.response.RegisterResponse
import com.dicoding.storyapp.repository.AuthenticationRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class AuthenticationViewModel(private val authenticationRepository: AuthenticationRepository) : ViewModel() {

    private val _registerResponse = MutableLiveData<RegisterResponse?>()
    val registerResponse: LiveData<RegisterResponse?> = _registerResponse

    private val _loginResponse = MutableLiveData<UserModel?>()
    val loginResponse: LiveData<UserModel?> = _loginResponse

    private val _invalidLoginResponse = MutableLiveData<LoginResponse?>(null)
    val invalidLoginResponse: LiveData<LoginResponse?> = _invalidLoginResponse

    fun defaultRegister() {
        _registerResponse.value = null
    }

    fun defaultLogin() {
        _invalidLoginResponse.value = null
    }

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                val message = authenticationRepository.register(name, email, password)
                _registerResponse.value = message
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, RegisterResponse::class.java)
                _registerResponse.value = errorBody
                Log.e(TAG, "onFailure: ${e.message}")
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = authenticationRepository.login(email, password)
                val userModel = UserModel()

                response.loginResult.apply {
                    userModel.userId = userId
                    userModel.token = token
                    userModel.name = name
                }

                _loginResponse.value = userModel
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, LoginResponse::class.java)
                _invalidLoginResponse.value = errorBody
            }
        }
    }

    companion object {
        private val TAG = AuthenticationViewModel::class.java.simpleName
    }
}