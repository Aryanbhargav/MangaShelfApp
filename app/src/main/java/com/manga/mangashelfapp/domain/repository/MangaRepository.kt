package com.manga.mangashelfapp.domain.repository

import android.media.MediaRouter.RouteCategory
import com.manga.mangashelfapp.domain.model.MangaListing
import com.manga.mangashelfapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface MangaRepository {
    suspend fun getMangaListing(fetchFromRemote: Boolean): Flow<Resource<List<MangaListing>>>
    suspend fun getFavoriteMangas(): List<MangaListing>
    suspend fun updateFavouriteManga(manga: MangaListing)
    suspend fun updateReadManga(manga: MangaListing)
    suspend fun getRecommendedManga(category: String):List<MangaListing>
}