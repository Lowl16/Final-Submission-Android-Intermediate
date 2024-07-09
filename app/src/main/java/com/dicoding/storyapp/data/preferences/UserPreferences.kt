package com.dicoding.storyapp.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.dicoding.storyapp.data.model.UserModel
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class UserPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    fun getSession(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            Gson().fromJson(preferences[SESSION] ?: "", UserModel::class.java)
        }
    }

    suspend fun saveSession(session: UserModel) {
        dataStore.edit { preferences ->
            preferences[SESSION] = Gson().toJson(session)
        }
    }

    suspend fun removeSession() {
        dataStore.edit { preferences ->
            preferences.remove(SESSION)
        }
    }

    companion object {
        private val SESSION = stringPreferencesKey("user_session")

        @Volatile
        private var INSTANCE: UserPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): UserPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}