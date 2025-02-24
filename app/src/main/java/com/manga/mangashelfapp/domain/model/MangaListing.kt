package com.manga.mangashelfapp.domain.model

data class MangaListing(
    val category: String,
    val id: String,
    val image: String,
    val popularity: Int,
    val publishedChapterDate: Int,
    val score: Double,
    val title: String,
    var isFavourite:Boolean,
    var isRead:Boolean
)
