package com.manga.mangashelfapp.presentation.manga_listings

import com.manga.mangashelfapp.domain.model.MangaListing

data class MangaListingState(
    var manga: List<MangaListing> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
)
