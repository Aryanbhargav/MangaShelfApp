package com.manga.mangashelfapp.data.mapper


import com.manga.mangashelfapp.data.local.MangaListingEntity
import com.manga.mangashelfapp.data.remote.dto.MangaListingDto
import com.manga.mangashelfapp.domain.model.MangaListing
import java.util.Calendar
import java.util.TimeZone

fun MangaListingDto.toMangaListingEntity(): MangaListingEntity {
    return MangaListingEntity(
        category = category,
        id = id,
        image = image,
        popularity = popularity,
        publishedChapterDate = publishedChapterDate,
        score = score,
        title = title
    )
}
fun MangaListing.toMangaListingEntity(): MangaListingEntity {
    return MangaListingEntity(
        category = category,
        id = id,
        image = image,
        popularity = popularity,
        publishedChapterDate = publishedChapterDate,
        score = score,
        title = title,
        isFavourite=isFavourite,
        isRead=isRead
    )
}

fun MangaListingEntity.toMangaListing(): MangaListing {
    val timestamp = if (publishedChapterDate < 10000000000L) { // If it's in seconds
        publishedChapterDate * 1000L
    } else {
        publishedChapterDate
    }

    // Use UTC to avoid timezone issues
    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
        timeInMillis = timestamp as Long
    }


    val resultantYear = calendar.get(Calendar.YEAR)
    return MangaListing(
        category = category ?:"",
        id = id,
        image = image,
        popularity = popularity,
        publishedChapterDate = resultantYear,
        score = score,
        title = title,
        isFavourite = isFavourite,
        isRead=isRead
    )
}