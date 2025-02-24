package com.manga.mangashelfapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manga.mangashelfapp.domain.model.MangaListing
import com.manga.mangashelfapp.ui.viewmodel.MangaViewModel

@Composable
fun ReadStatusChip(mangaListing: MangaListing, mangaViewModel: MangaViewModel) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .border(
                width = if (mangaListing.isRead) 0.dp else 1.dp,
                color = if (mangaListing.isRead) Color.Transparent else Color.White,
                shape = RoundedCornerShape(16.dp)
            )
            .background(
                color = if (mangaListing.isRead) Color(0xFF4CAF50) else Color.Transparent, // Green if read
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable {
                mangaViewModel.toggleRead(mangaListing)
            }
    ) {
        Text(
            text = if (mangaListing.isRead) "Read" else "Unread",
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
