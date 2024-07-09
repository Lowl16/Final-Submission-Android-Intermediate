package com.dicoding.storyapp.ui.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.storyapp.R
import com.dicoding.storyapp.data.model.UserModel
import com.dicoding.storyapp.data.preferences.UserPreferences
import com.dicoding.storyapp.data.preferences.dataStore
import com.dicoding.storyapp.databinding.ActivityLoginBinding
import com.dicoding.storyapp.ui.viewmodel.AuthenticationViewModel
import com.dicoding.storyapp.ui.viewmodel.AuthenticationViewModelFactory
import com.dicoding.storyapp.ui.viewmodel.UserPreferencesViewModel
import com.dicoding.storyapp.ui.viewmodel.UserPreferencesViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val authenticationViewModelFactory: AuthenticationViewModelFactory = AuthenticationViewModelFactory.getInstance()
    private val authenticationViewModel: AuthenticationViewModel by viewModels { authenticationViewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val userPreferences: UserPreferences = UserPreferences.getInstance(application.dataStore)
        val userPreferencesViewModelFactory: UserPreferencesViewModelFactory = UserPreferencesViewModelFactory.getInstance(userPreferences)
        val userPreferencesViewModel: UserPreferencesViewModel by viewModels { userPreferencesViewModelFactory }

        binding.btnLogin.setOnClickListener {
            val login = binding.btnLogin
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                showLoading(login, true)
                authenticationViewModel.login(email, password)

                authenticationViewModel.loginResponse.observe(this) { session ->
                    if (session != null) {
                        Toast.makeText(this, getString(R.string.welcome), Toast.LENGTH_SHORT).show()

                        val userModel = UserModel(
                            session.userId,
                            session.token,
                            session.name
                        )

                        userPreferencesViewModel.saveSession(userModel)

                        showLoading(login, false)

                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    }
                }

                authenticationViewModel.invalidLoginResponse.observe(this) { invalidLogin ->
                    val invalid = invalidLogin?.message.toString()
                    if (invalidLogin != null) {
                        showLoading(login, false)

                        Toast.makeText(this, invalid, Toast.LENGTH_SHORT).show()
                        authenticationViewModel.defaultLogin()
                    }
                }
            } else {
                Toast.makeText(this, getString(R.string.invalid_input), Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        playAnimation()
    }

    private fun showLoading(login: Button, isLoading: Boolean) { login.text = if (!isLoading) getString(R.string.login) else getString(R.string.loading) }

    private fun playAnimation() {
        val logo = ObjectAnimator.ofFloat(binding.ivLogo, View.ALPHA, 1f).setDuration(150)
        val title = ObjectAnimator.ofFloat(binding.tvWelcome, View.ALPHA, 1f).setDuration(150)
        val titleDescription = ObjectAnimator.ofFloat(binding.tvLoginDescription, View.ALPHA, 1f).setDuration(150)
        val email = ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(150)
        val emailInput = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(150)
        val password = ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f).setDuration(150)
        val passwordInput = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(150)
        val login = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(150)
        val signUp = ObjectAnimator.ofFloat(binding.btnSignUp, View.ALPHA, 1f).setDuration(150)

        AnimatorSet().apply {
            playSequentially(logo, title, titleDescription, email, emailInput, password, passwordInput, login, signUp)
            start()
        }
    }
}