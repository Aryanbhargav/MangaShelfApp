package com.manga.mangashelfapp.presentation.manga_listings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.manga.mangashelfapp.domain.model.MangaListing
import com.manga.mangashelfapp.ui.viewmodel.MangaViewModel
import kotlinx.coroutines.launch

@Composable
fun YearTabsMangaList(
    state: MangaListingState,
    navHostController: NavHostController,
    mangaViewModel: MangaViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    if (state.manga.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator() // Show loading spinner
        }
    } else {
        state.manga = state.manga.sortedBy { it.publishedChapterDate }
        val yearToMangaIndex = remember {
            state.manga.groupBy { it.publishedChapterDate }
                .mapValues { entry -> state.manga.indexOfFirst { it.publishedChapterDate == entry.key } }
        }
        val years = yearToMangaIndex.keys.sorted() // Extract years with mangas
        var selectedYear =
            remember { mutableIntStateOf(years.first()) } // Default to first available year

        val listState = rememberLazyListState()
        ScrollableTabRow(
            selectedTabIndex = years.indexOf(selectedYear.intValue),
            modifier = Modifier.fillMaxWidth(),
            edgePadding = 8.dp,
            containerColor = Color.DarkGray,
            contentColor = Color(0xFFFFA500)
        ) {
            years.forEachIndexed { index, year ->
                Tab(
                    selected = year == selectedYear.intValue,
                    onClick = {
                        selectedYear.intValue = year
                        yearToMangaIndex[year]?.let { index ->
                            coroutineScope.launch {
                                listState.scrollToItem(index)
                                listState.animateScrollToItem(index)
                            }
                        }
                    }
                ) {
                    Text(
                        text = year.toString(),
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }

        LazyColumn(
            state = listState,
            modifier = Modifier
                .background(Color.Black)
                .fillMaxSize()
        ) {
            itemsIndexed(state.manga,key={ _, mangaListing -> mangaListing.id }) { i: Int, mangaListing: MangaListing ->
                MangaItemBookRow(
                    mangaListing,
                    mangaViewModel

                ) {
                    mangaViewModel.updateSingleMangaList(mangaListing)
                    mangaViewModel.selectedMangaDetails.value = mangaListing
                    navHostController.navigate("manga_details_screen")
                }

                LaunchedEffect(remember { derivedStateOf { listState.firstVisibleItemIndex } }) {
                    val currentYear =
                        state.manga[listState.firstVisibleItemIndex].publishedChapterDate
                    if (selectedYear.intValue != currentYear) {
                        selectedYear.intValue = currentYear
                    }
                }


            }
        }
    }
}