package com.manga.mangashelfapp.presentation.manga_favourites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.manga.mangashelfapp.presentation.components.MangaHeading
import com.manga.mangashelfapp.presentation.manga_listings.MangaItemBookRow
import com.manga.mangashelfapp.ui.viewmodel.MangaViewModel

@Composable
fun MangaFavouritesScreen(mangaViewModel: MangaViewModel, navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize()){
        val favoriteMangaList=mangaViewModel.favoriteMangaList.collectAsState()
        favoriteMangaList.value.let { it->
            LazyColumn (modifier = Modifier.fillMaxSize().padding(top=32.dp)){
                item{
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "favourite",
                            tint =  Color(0xFFFAAF25),
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .clickable (interactionSource = remember { MutableInteractionSource() },
                                    indication = null){
                                    navController.popBackStack()
                                }
                        )
                        MangaHeading("Favourites")
                    }
                }
                items(it.size){ i->
                    MangaItemBookRow(it[i],mangaViewModel) {
                        mangaViewModel.updateSingleMangaList(it[i])
                        mangaViewModel.selectedMangaDetails.value = it[i]
                        navController.navigate("manga_details_screen")
                    }
                }
            }
        }
    }
}