package com.manga.mangashelfapp.presentation.manga_details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.manga.mangashelfapp.domain.model.MangaListing
import com.manga.mangashelfapp.R
import com.manga.mangashelfapp.presentation.components.ChipView
import com.manga.mangashelfapp.presentation.components.MangaHeading
import com.manga.mangashelfapp.presentation.components.ReadStatusChip
import com.manga.mangashelfapp.ui.viewmodel.MangaViewModel



@Composable
fun MangaDetailsScreen(mangaViewModel: MangaViewModel, navController: NavHostController) {

    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.onSurface)
            .fillMaxSize()
    ) {

        val title = mangaViewModel.selectedMangaDetails.value.title
        val thumbnailUrl = mangaViewModel.selectedMangaDetails.value.image
        val categories = mangaViewModel.selectedMangaDetails.value.category
        val score = mangaViewModel.selectedMangaDetails.value.score
        val popularity = mangaViewModel.selectedMangaDetails.value.popularity
        val state = mangaViewModel.state
        val listOfMangaListing= state.manga.filter { it.title == title }
        var mangaListing:MangaListing=MangaListing("","","",0,0,0.00,"",false,false)
        if(!listOfMangaListing.isNullOrEmpty()){
            mangaListing=listOfMangaListing[0]
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 16.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(color = Color.Transparent),
            contentAlignment = Alignment.Center
        ) {

            Column(modifier = Modifier.padding(top = 40.dp)) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "back",
                        tint = Color(0xFFFAAF25),
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                navController.popBackStack()
                            }
                    )
                    MangaHeading("Manga Detail")
                }
                mangaListing?.let {
                    BookImageContentView(
                        title,
                        score,
                        thumbnailUrl,
                        categories,
                        popularity,
                        mangaViewModel,
                        it
                    )
                }
            }

        }
    }
}


@Composable
fun BookImageContentView(
    title: String,
    score: Double,
    thumbnailUrl: String,
    categories: String,
    popularity: Int,
    mangaViewModel: MangaViewModel,
    mangaListing: MangaListing
) {
    // content
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())
            ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (mangaListing.isRead) "Mark as Unread : " else "Mark as Read : ",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
                color = Color(0xFFFFC107)
            )

            ReadStatusChip(mangaListing, mangaViewModel)
        }

        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(thumbnailUrl)
                .crossfade(true)
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.error_img)
                .build(),
            contentDescription = "Loaded Image",
            modifier = Modifier
                .size(300.dp, 400.dp)
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )
        Icon(
            imageVector = if (mangaListing.isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = "favourite",
            tint = if (mangaListing.isFavourite) Color.Red else Color.White,
            modifier = Modifier
                .size(60.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    mangaViewModel.toggleFavorite(mangaListing)
                }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onSurface)
                .padding(bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Text(
                    text = "Title : ",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Thin
                    ),
                    textAlign = TextAlign.Center,
                    color = Color(0xFFFFC107)
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                InfoItem(icon = Icons.Default.Star, label = "Score", value = score.toString())
                InfoItem(
                    icon = Icons.Default.ThumbUp,
                    label = "Popularity",
                    value = popularity.toString()
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            ChipView(txt = categories)
        }
        if (mangaListing.isRead) {
            Box(modifier = Modifier.fillMaxWidth()) {
                MangaHeading("Recommendations", 22)
            }
            Spacer(modifier = Modifier.height(16.dp))
            mangaViewModel.getRecommendedList(categories)
            val recommendedList=mangaViewModel.recommendedManga.collectAsState()
            recommendedList.value.let {it->
                LazyRow() {
                    items(it.size){ i->
                        val manga=it[i]
                        RecommendedItem(manga)
                    }
                }
            }
            Spacer(modifier = Modifier.height(48.dp))
        }
    }

}

@Composable
fun RecommendedItem(manga: MangaListing) {
    Card(
        modifier = Modifier
            .background(Color.Black.copy(alpha = 0.3f))
            .wrapContentSize()
            .clip(RoundedCornerShape(20.dp))
            .padding(8.dp)
    ) {

        val context = LocalContext.current

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onSurface)
                .padding(12.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = ImageRequest.Builder(context)
                     .data(manga.image)
                    .crossfade(true)
                    .placeholder(R.drawable.place_holder)
                    .error(R.drawable.error_img)
                    .build(),
                contentDescription = "Loaded Image",
                modifier = Modifier
                    .size(width = 80.dp, height = 165.dp)
                    .padding(horizontal = 16.dp),
                contentScale = ContentScale.Inside
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column() {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = manga.title, color = Color.White, maxLines = 2)
                Text(text = "Score : ${manga.score}", color = Color.White)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Popularity : ${manga.popularity}", color = Color.White)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun InfoItem(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = icon, contentDescription = label, tint = Color(0xFFFFC107))
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "$label: $value", style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            color = Color.White
        )
    }
}