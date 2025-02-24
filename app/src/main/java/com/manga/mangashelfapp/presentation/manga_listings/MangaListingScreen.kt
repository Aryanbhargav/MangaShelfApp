package com.manga.mangashelfapp.presentation.manga_listings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.manga.mangashelfapp.domain.model.MangaListing
import com.manga.mangashelfapp.presentation.components.FilterWithBottomSheet
import com.manga.mangashelfapp.presentation.components.MangaHeading
import com.manga.mangashelfapp.ui.viewmodel.MangaViewModel


@Composable
fun MangaListingScreen(
    navHostController: NavHostController,
    mangaViewModel: MangaViewModel
) {

    var state = mangaViewModel.state

    val sortingOption=mangaViewModel.sortingOption
    var showSheet = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.onSurface),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MangaHeading("MangaShelf")
            Spacer(modifier = Modifier.weight(1F))
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorites",
                    tint = Color.White,
                    modifier = Modifier
                        .clickable(interactionSource = remember { MutableInteractionSource() },
                            indication = null) {
                            navHostController.navigate("manga_favourite_screen")
                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
                Text(
                    fontSize = 12.sp,
                    text = "Favorites",
                    color = Color(0xFFFFA500)
                )
            }
            Spacer(modifier = Modifier.weight(0.2F))


            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Filter",
                    tint = Color.White,
                    modifier = Modifier
                        .clickable(interactionSource = remember { MutableInteractionSource() },
                            indication = null) {
                            showSheet.value = !showSheet.value
                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )

                Text(
                    fontSize = 12.sp,
                    text = "Filter",
                    color = Color(0xFFFFA500)
                )
            }


        }

        if (showSheet.value) {
            FilterWithBottomSheet(showSheet, sortingOption)
              if (sortingOption.value == SortingOption.YEAR)
                  YearTabsMangaList(state, navHostController, mangaViewModel)
              else {
                  val sortedMangas = remember(sortingOption, state.manga) {
                      when (sortingOption.value) {
                          SortingOption.YEAR -> state.manga.sortedBy { it.publishedChapterDate }
                          SortingOption.SCORE_ASC -> state.manga.sortedBy { it.score }
                          SortingOption.SCORE_DESC -> state.manga.sortedByDescending { it.score }
                          SortingOption.POPULARITY_ASC -> state.manga.sortedBy { it.popularity }
                          SortingOption.POPULARITY_DESC -> state.manga.sortedByDescending { it.popularity }
                      }
                  }
               SortBasedMangaList(sortedMangas, navHostController, mangaViewModel)
           }

        }
        else if(sortingOption.value != SortingOption.YEAR) {
            val sortedMangas = remember(sortingOption, state.manga) {
                when (sortingOption.value) {
                    SortingOption.YEAR -> state.manga.sortedBy { it.publishedChapterDate }
                    SortingOption.SCORE_ASC -> state.manga.sortedBy { it.score }
                    SortingOption.SCORE_DESC -> state.manga.sortedByDescending { it.score }
                    SortingOption.POPULARITY_ASC -> state.manga.sortedBy { it.popularity }
                    SortingOption.POPULARITY_DESC -> state.manga.sortedByDescending { it.popularity }
                }
            }
            SortBasedMangaList(sortedMangas, navHostController, mangaViewModel)
        }else{
            YearTabsMangaList(state, navHostController, mangaViewModel)
        }
    }


}

@Composable
fun SortBasedMangaList(
    sortedMangas: List<MangaListing>,
    navHostController: NavHostController,
    mangaViewModel: MangaViewModel
) {
    LazyColumn {
        items(sortedMangas) { manga ->
            MangaItemBookRow(
                manga,
                mangaViewModel
            ) {
                mangaViewModel.updateSingleMangaList(manga)
                mangaViewModel.selectedMangaDetails.value = manga
                navHostController.navigate("manga_details_screen")

            }
        }
    }
}

enum class SortingOption {
    SCORE_ASC, SCORE_DESC, POPULARITY_ASC, POPULARITY_DESC, YEAR
}