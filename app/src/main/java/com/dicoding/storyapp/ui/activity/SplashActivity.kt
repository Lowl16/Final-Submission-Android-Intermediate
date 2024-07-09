package com.dicoding.storyapp.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.storyapp.R
import com.dicoding.storyapp.data.preferences.UserPreferences
import com.dicoding.storyapp.data.preferences.dataStore
import com.dicoding.storyapp.ui.viewmodel.UserPreferencesViewModel
import com.dicoding.storyapp.ui.viewmodel.UserPreferencesViewModelFactory

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val splashDuration: Long = 1500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val userPreferences: UserPreferences = UserPreferences.getInstance(application.dataStore)
        val userPreferencesViewModelFactory: UserPreferencesViewModelFactory = UserPreferencesViewModelFactory.getInstance(userPreferences)
        val userPreferencesViewModel: UserPreferencesViewModel by viewModels { userPreferencesViewModelFactory }

        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            val mainIntent = Intent(this, MainActivity::class.java)
            val loginIntent = Intent(this, LoginActivity::class.java)

            userPreferencesViewModel.getSession().observe(this) { session ->
                if (session != null) {
                    Toast.makeText(this, getString(R.string.welcome), Toast.LENGTH_SHORT).show()
                    startActivity(mainIntent)
                } else {
                    startActivity(loginIntent)
                }
            }
            finish()
        }, splashDuration)
    }
}