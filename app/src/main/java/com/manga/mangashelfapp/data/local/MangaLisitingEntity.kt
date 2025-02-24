package com.manga.mangashelfapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MangaListingEntity(
    val category: String,
    @PrimaryKey val id: String,
    val image: String,
    val popularity: Int,
    val publishedChapterDate: Int,
    val score: Double,
    val title: String,
    var isFavourite: Boolean = false,
    var isRead:Boolean=false
)