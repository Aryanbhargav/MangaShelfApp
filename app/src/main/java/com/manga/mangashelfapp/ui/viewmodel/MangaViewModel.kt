package com.manga.mangashelfapp.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manga.mangashelfapp.domain.model.MangaListing
import com.manga.mangashelfapp.domain.repository.MangaRepository
import com.manga.mangashelfapp.util.Resource
import com.manga.mangashelfapp.presentation.manga_listings.MangaListingState
import com.manga.mangashelfapp.presentation.manga_listings.SortingOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MangaViewModel @Inject constructor(var mangaRepository : MangaRepository)  : ViewModel(){

    val showSortingOptions = mutableStateOf(false)
    var state by mutableStateOf(MangaListingState())
    val selectedMangaDetails = mutableStateOf(MangaListing("","","",0,0,0.00,"",false,false))
    private val _favoriteMangaList = MutableStateFlow<List<MangaListing>>(emptyList())
    val favoriteMangaList: StateFlow<List<MangaListing>> get() = _favoriteMangaList

    private val _singleMangaListing = MutableStateFlow<MangaListing?>((null))
    val singleMangaListing: StateFlow<MangaListing?> get() = _singleMangaListing

    var sortingOption =  mutableStateOf<SortingOption>(SortingOption.YEAR)
    init {
        getMangaList()
        loadFavorites()
    }

    fun updateSingleMangaList(manga: MangaListing) {
        _singleMangaListing.value=manga
    }

    fun getMangaList( fetchFromRemote: Boolean = false){
        viewModelScope.launch {
            mangaRepository.getMangaListing(fetchFromRemote)
                .collect{ result->
                    when(result) {
                    is Resource.Success -> {
                        result.data?.let { listings ->
                            println("getMangaList Listing $listings")
                            state = state.copy(
                                manga = listings
                            )
                        }
                    }
                    is Resource.Error -> Unit
                    is Resource.Loading -> {
                       state = state.copy(isLoading = result.isLoading)
                    }
                }

                }
        }

    }
    private fun loadFavorites() {
        viewModelScope.launch {
            _favoriteMangaList.value = mangaRepository.getFavoriteMangas()
        }
    }
    fun toggleFavorite(manga: MangaListing) {
        viewModelScope.launch {
            val newFavoriteStatus = !manga.isFavourite
            mangaRepository.updateFavouriteManga(manga.copy(isFavourite = newFavoriteStatus))

            val updatedManga = state.manga.map {
                if (it.id == manga.id) {
                    it.copy( isFavourite = newFavoriteStatus)
                } else it
            }
            state=state.copy(
                manga=updatedManga
            )

            loadFavorites()
        }
    }
    fun toggleRead(manga: MangaListing){
        viewModelScope.launch {
            val newReadStatus=!manga.isRead
            mangaRepository.updateReadManga(manga.copy(isRead=newReadStatus))
            val updatedManga=state.manga.map{
                if(it.id==manga.id){
                    it.copy(isRead = newReadStatus)
            }
                else{
                    it
                }
            }
            state=state.copy(
                manga=updatedManga
            )
        }
    }
}