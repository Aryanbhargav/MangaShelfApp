package com.manga.mangashelfapp.presentation.manga_listings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable
fun SortingOptions(onSortSelected: (SortingOption) -> Unit, sortingOption: SortingOption) {
    val options = listOf(
        SortingOption.YEAR to "Year",
        SortingOption.SCORE_ASC to "Score -- Low to High ",
        SortingOption.SCORE_DESC to "Score -- High to Low",
        SortingOption.POPULARITY_ASC to "Popularity -- Low to High",
        SortingOption.POPULARITY_DESC to "Popularity -- High to Low",

        )


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sort By",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(16.dp))
        options.forEach { (option, label) ->
            Button(
                modifier = Modifier.align(Alignment.Start).fillMaxWidth(),
                onClick = {
                    onSortSelected(option) },
                colors =   if(sortingOption == option)
                     ButtonDefaults.buttonColors(Color(0xFFFFA500))
                        else
                    ButtonDefaults.buttonColors(
                        Color.Black.copy(0.8f)
                )
            ) {
                Text(label)
            }

        }
    }
}
