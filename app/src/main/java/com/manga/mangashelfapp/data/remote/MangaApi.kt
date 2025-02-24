package com.manga.mangashelfapp.data.remote

import com.manga.mangashelfapp.data.remote.dto.MangaListingDto
import retrofit2.http.GET

interface MangaApi {



    @GET("b/KEJO")
    suspend fun getMangaList():List<MangaListingDto>

    companion object {
        const val BASE_URL = "https://www.jsonkeeper.com/"
    }
}