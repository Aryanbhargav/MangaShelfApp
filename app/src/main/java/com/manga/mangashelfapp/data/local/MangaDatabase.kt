package com.manga.mangashelfapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MangaListingEntity::class],
    version = 1)
abstract class MangaDatabase :RoomDatabase(){
    abstract val dao:MangaDao
}