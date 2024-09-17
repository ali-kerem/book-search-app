package com.example.booksearchapp.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey val id: String,
    val description: String?,
    @Embedded(prefix = "volume_") val volumeInfo: VolumeInfoEntity,
    @Embedded(prefix = "sale_") val saleInfo: SaleInfoEntity?
)

data class VolumeInfoEntity(
    val title: String?,
    val subtitle: String?,
    val description: String?,
    @Embedded(prefix = "image_") val imageLinks: ImageLinksEntity?,
    val authors: List<String>?,
    val publisher: String?,
    val publishedDate: String?
)

data class SaleInfoEntity(
    val country: String?,
    val isEbook: Boolean?,
    @Embedded(prefix = "listPrice_") val listPrice: ListPriceEntity?
)

data class ImageLinksEntity(
    val smallThumbnail: String,
    val thumbnail: String
)

data class ListPriceEntity(
    val amount: Float?,
    val currency: String? = "TRY"
)
