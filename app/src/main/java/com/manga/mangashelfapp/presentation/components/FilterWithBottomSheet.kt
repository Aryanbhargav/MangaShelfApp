package com.manga.mangashelfapp.presentation.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import com.manga.mangashelfapp.presentation.manga_listings.SortingOption
import com.manga.mangashelfapp.presentation.manga_listings.SortingOptions
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterWithBottomSheet(
    showSheet: MutableState<Boolean>,
    sortingOption: MutableState<SortingOption>
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    if (showSheet.value) {
        ModalBottomSheet(
            onDismissRequest = { showSheet.value = !showSheet.value },
            sheetState = sheetState,
            containerColor = Color.White
        ) {
            SortOptionsBottomSheet(sortingOption.value) { selectedSort ->
                sortingOption.value = selectedSort
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    showSheet.value = !showSheet.value // Close the bottom sheet after hiding
                }
            }
        }
    }
}

@Composable
fun SortOptionsBottomSheet(sortingOption: SortingOption, onSortSelected: (SortingOption) -> Unit) {
    SortingOptions(onSortSelected = onSortSelected, sortingOption)
}
