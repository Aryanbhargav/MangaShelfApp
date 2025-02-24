package com.manga.mangashelfapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.manga.mangashelfapp.presentation.manga_details.MangaDetailsScreen
import com.manga.mangashelfapp.presentation.manga_favourites.MangaFavouritesScreen
import com.manga.mangashelfapp.presentation.manga_listings.MangaListingScreen
import com.manga.mangashelfapp.ui.theme.MangaShelfAppTheme
import com.manga.mangashelfapp.ui.viewmodel.MangaViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            MangaShelfAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.onSurface
                ) {
                    NavGraphCustom()
                }
            }
        }
    }


}

@Composable
fun NavGraphCustom() {
    val navController = rememberNavController()
    val mangaViewModel: MangaViewModel = hiltViewModel()
    NavHost(navController = navController, startDestination = "manga_listing_screen") {
        composable("manga_listing_screen") {
            MangaListingScreen(navHostController = navController, mangaViewModel)
        }
        composable("manga_details_screen") {
            MangaDetailsScreen(mangaViewModel,navController)
        }
        composable("manga_favourite_screen") {
            MangaFavouritesScreen(mangaViewModel,navController)
        }
    }
}
