package com.dicoding.storyapp.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.storyapp.R
import com.dicoding.storyapp.data.preferences.UserPreferences
import com.dicoding.storyapp.data.preferences.dataStore
import com.dicoding.storyapp.databinding.ActivityMainBinding
import com.dicoding.storyapp.ui.adapter.LoadingStateAdapter
import com.dicoding.storyapp.ui.adapter.StoryAdapter
import com.dicoding.storyapp.ui.viewmodel.StoryViewModel
import com.dicoding.storyapp.ui.viewmodel.StoryViewModelFactory
import com.dicoding.storyapp.ui.viewmodel.UserPreferencesViewModel
import com.dicoding.storyapp.ui.viewmodel.UserPreferencesViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var storyViewModel: StoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val userPreferences: UserPreferences = UserPreferences.getInstance(application.dataStore)
        val userPreferencesViewModelFactory: UserPreferencesViewModelFactory = UserPreferencesViewModelFactory.getInstance(userPreferences)
        val userPreferencesViewModel: UserPreferencesViewModel by viewModels { userPreferencesViewModelFactory }

        storyViewModel = obtainViewModel(this@MainActivity)

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java))
        }

        binding.actionMap.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }

        binding.actionLogout.setOnClickListener {
            val builder = AlertDialog.Builder(this)

            builder.setTitle(getString(R.string.logout))
            builder.setMessage(getString(R.string.logout_confirmation))

            builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
                userPreferencesViewModel.removeSession()

                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }

            builder.setNegativeButton(getString(R.string.no)) { dialog, _ ->
                dialog.dismiss()
            }

            builder.show()
        }

        setStoryData()
    }

    private fun obtainViewModel(appCompatActivity: AppCompatActivity): StoryViewModel {
        val storyViewModelFactory = StoryViewModelFactory.getInstance(appCompatActivity.applicationContext)

        return ViewModelProvider(
            appCompatActivity, storyViewModelFactory
        )[StoryViewModel::class.java]
    }

    private fun setStoryData() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvStory.layoutManager = layoutManager

        val adapter = StoryAdapter()

        binding.rvStory.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )

        storyViewModel.getStories.observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }
}