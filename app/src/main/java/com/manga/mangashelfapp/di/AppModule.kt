package com.manga.mangashelfapp.di

import android.app.Application
import androidx.room.Room
import com.manga.mangashelfapp.data.local.MangaDatabase
import com.manga.mangashelfapp.data.remote.MangaApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object AppModule {

    @Provides
    @Singleton
    fun provideMangaApi():MangaApi{
        return Retrofit.Builder()
            .baseUrl(MangaApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }).build())
            .build()
            .create()
    }
    @Provides
    @Singleton
    fun provideMangaDatabase(app:Application):MangaDatabase{
        return Room.databaseBuilder(
            app,
            MangaDatabase::class.java,
            "mangadb.db"
        )
            .build()
    }
}