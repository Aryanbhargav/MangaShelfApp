package com.manga.mangashelfapp.data.repository

import com.manga.mangashelfapp.data.local.MangaDatabase
import com.manga.mangashelfapp.data.mapper.toMangaListing
import com.manga.mangashelfapp.data.mapper.toMangaListingEntity
import com.manga.mangashelfapp.data.remote.MangaApi
import com.manga.mangashelfapp.domain.model.MangaListing
import com.manga.mangashelfapp.domain.repository.MangaRepository
import com.manga.mangashelfapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MangaRepositoryImpl @Inject constructor(
    private val mangaApi: MangaApi,
    private val db: MangaDatabase
) : MangaRepository {

    private val dao = db.dao

    override suspend fun getMangaListing(
        fetchFromRemote: Boolean
    ): Flow<Resource<List<MangaListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localMangaListings = dao.getAnimeList()
            emit(
                Resource.Success(
                    data = localMangaListings.map {
                        it.toMangaListing()
                    }
                )
            )

            val isDbEmpty = localMangaListings.isEmpty()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteListings = try {
                mangaApi.getMangaList()
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }
            println("Check Response :$remoteListings")
            remoteListings?.let { listings ->
                dao.clearMangaListings()
                dao.insertMangaListings(
                    listings.map { it.toMangaListingEntity() }
                )
                emit(
                    Resource.Success(
                        data = dao
                            .getAnimeList()
                            .map { it.toMangaListing() }
                    ))
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getFavoriteMangas(): List<MangaListing> {
        return dao.getFavoriteMangas().map { it.toMangaListing() }
    }

    override suspend fun updateFavouriteManga(manga: MangaListing) {
        dao.updateFavoriteStatus(manga.id, manga.isFavourite)
    }
    override suspend fun updateReadManga(manga: MangaListing) {
        dao.updateReadStatus(manga.id, manga.isRead)
    }

    override suspend fun getRecommendedManga(category: String): List<MangaListing> {
       return dao.getRecommendedManga(category).map { it.toMangaListing() }
    }

}