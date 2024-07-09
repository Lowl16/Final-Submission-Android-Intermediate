package com.dicoding.storyapp.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.storyapp.data.response.Story
import com.dicoding.storyapp.databinding.ActivityDetailBinding
import com.dicoding.storyapp.ui.viewmodel.StoryViewModel
import com.dicoding.storyapp.ui.viewmodel.StoryViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.math.abs

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val storyViewModelFactory: StoryViewModelFactory = StoryViewModelFactory.getInstance(this@DetailActivity)
        val storyViewModel: StoryViewModel by viewModels { storyViewModelFactory }

        val storyId = STORY_ID

        storyViewModel.getDetailStories(storyId)

        storyViewModel.detailStories.observe(this) { detailStories ->
            setDetailStories(detailStories)
        }
    }

    private fun setDetailStories(detailStories: Story) {
        with(binding) {
            tvDetailName.text = detailStories.name
            tvDetailDescription.text = detailStories.description
            tvDetailDate.text = formatDate(detailStories.createdAt)
            Glide.with(this@DetailActivity)
                .load(detailStories.photoUrl)
                .into(ivDetailPhoto)
        }
    }

    private fun formatDate(dateString: String): String {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale("id", "ID"))
        val date = format.parse(dateString) as Date

        var outputFormat = SimpleDateFormat("dd/MM/yy, HH:mm:ss", Locale("id", "ID"))
        outputFormat.timeZone = TimeZone.getTimeZone("GMT+7")

        val createdAt = Date(date.time + (7 * 60 * 60 * 1000))
        val today = Date()

        return if (convertTime(today, createdAt)) {
            outputFormat = SimpleDateFormat("HH:mm:ss", Locale("id", "ID"))
            "Yesterday, ${outputFormat.format(Date(date.time + (7 * 60 * 60 * 1000)))}"
        } else {
            outputFormat.format(Date(date.time + (7 * 60 * 60 * 1000)))
        }
    }

    private fun convertTime(date1: Date, date2: Date): Boolean {
        val difference = date1.time - date2.time
        val oneDayInMillis = 24 * 60 * 60 * 1000

        return abs(difference).toInt() >= oneDayInMillis
    }

    companion object {
        var STORY_ID = "story_id"
    }
}