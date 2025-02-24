package com.manga.mangashelfapp.data.remote.dto

data class MangaListingDto(
    val category: String,
    val id: String,
    val image: String,
    val popularity: Int,
    val publishedChapterDate: Int,
    val score: Double,
    val title: String,
)
