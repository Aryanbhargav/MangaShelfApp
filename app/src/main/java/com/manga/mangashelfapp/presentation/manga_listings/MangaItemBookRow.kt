package com.manga.mangashelfapp.presentation.manga_listings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.manga.mangashelfapp.data.mapper.toMangaListingEntity
import com.manga.mangashelfapp.domain.model.MangaListing
import com.manga.mangashelfapp.R
import com.manga.mangashelfapp.presentation.components.ChipView
import com.manga.mangashelfapp.ui.viewmodel.MangaViewModel

@Composable
fun MangaItemBookRow(
    mangaListing: MangaListing,
    mangaViewModel:MangaViewModel,
    onItemClick: () -> Unit
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .clickable(onClick = onItemClick)
            .background(Color.Black.copy(alpha = 0.3f))
            .fillMaxWidth()
            .wrapContentHeight()

            .clip(RoundedCornerShape(20.dp))
            .padding(8.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onSurface)
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(mangaListing.image)
                    .crossfade(true)
                    .placeholder(R.drawable.place_holder)
                    .error(R.drawable.error_img)
                    .build(),
                contentDescription = "Loaded Image",
                modifier = Modifier
                    .size(98.dp, 145.dp)
                    .weight(0.3f)
                    .padding(16.dp),
                contentScale = ContentScale.Inside
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(Modifier.weight(0.5f)) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = mangaListing.title, color = Color.White, maxLines = 2)
                Text(text = "Score : ${mangaListing.score}", color = Color.White)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Popularity : ${mangaListing.score}", color = Color.White)
                Spacer(modifier = Modifier.height(12.dp))
                ChipView( mangaListing.publishedChapterDate.toString())
                Spacer(modifier = Modifier.width(16.dp))
            }

            Icon(
                imageVector = if (mangaListing.isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "favourite",
                tint = if (mangaListing.isFavourite) Color.Red else Color.White,
                modifier = Modifier
                    .weight(0.2f)
                    .clickable (interactionSource = remember { MutableInteractionSource() },
                        indication = null){
                        mangaViewModel.toggleFavorite(mangaListing)
                    }
            )
        }
    }

}
