package com.manga.mangashelfapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface MangaDao {

    @Query("SELECT * FROM mangaListingEntity")
    suspend fun getAnimeList(): List<MangaListingEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMangaListings(
        mangaListingDto: List<MangaListingEntity>
    )
    @Update
    suspend fun updateManga(manga: MangaListingEntity)
    @Query("DELETE FROM mangalistingentity")
    suspend fun clearMangaListings()

    @Query("UPDATE mangalistingentity SET isFavourite = :isFavorite WHERE id = :mangaId")
    suspend fun updateFavoriteStatus(mangaId: String, isFavorite: Boolean)

    @Query("UPDATE mangalistingentity SET isRead = :isRead WHERE id = :mangaId")
    suspend fun updateReadStatus(mangaId: String, isRead: Boolean)


    @Query("SELECT * FROM mangalistingentity WHERE isFavourite = 1")
    suspend fun getFavoriteMangas(): List<MangaListingEntity>
}