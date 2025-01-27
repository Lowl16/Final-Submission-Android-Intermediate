package com.dicoding.storyapp.data.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "story")
@Parcelize
data class StoryEntity(

    @PrimaryKey
    val id: String,

    val name: String,

    val description: String,

    val photoURL: String,

    val createdAt: String,

    val lat: Double,

    val lon: Double
): Parcelable