package com.dicoding.storyapp.ui.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.storyapp.R
import com.dicoding.storyapp.data.database.StoryEntity
import com.dicoding.storyapp.databinding.ItemRowStoryBinding
import com.dicoding.storyapp.ui.activity.DetailActivity

class StoryAdapter : PagingDataAdapter<StoryEntity, StoryAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRowStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val story = getItem(position)

        if (story != null) {
            holder.bind(story)
        }

        holder.itemView.setOnClickListener {
            val detailStory = Intent(holder.itemView.context, DetailActivity::class.java)

            if (story != null) {
                DetailActivity.STORY_ID = story.id
            }

            val ivPhoto = holder.itemView.findViewById<ImageView>(R.id.iv_item_photo)

            if (ivPhoto != null) {
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    holder.itemView.context as Activity,
                    Pair(ivPhoto, "photo")
                ).toBundle()

                holder.itemView.context.startActivity(detailStory, options)
            } else {
                holder.itemView.context.startActivity(detailStory)
            }
        }
    }

    class MyViewHolder(private val binding: ItemRowStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(storyEntity: StoryEntity) {
            binding.tvItemName.text = storyEntity.name
            binding.tvItemDescription.text = storyEntity.description
            Glide.with(binding.ivItemPhoto.context)
                .load(storyEntity.photoURL)
                .into(binding.ivItemPhoto)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryEntity>() {
            override fun areItemsTheSame(oldItem: StoryEntity, newItem: StoryEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: StoryEntity, newItem: StoryEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}