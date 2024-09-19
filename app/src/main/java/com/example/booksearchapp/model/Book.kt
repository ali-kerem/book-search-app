package com.example.booksearchapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(
    val id: String,
    val description: String?,
    val volumeInfo: VolumeInfo,
    val saleInfo: SaleInfo
) : Parcelable {
    fun getPrice(): String? {
        return saleInfo.listPrice?.let { listPrice ->
            "${listPrice.amount} ${listPrice.currency ?: "TRY"}"
        }
    }
}

@Parcelize
data class VolumeInfo(
    val title: String?,
    val subtitle: String?,
    val description: String?,
    val imageLinks: ImageLinks? = null,
    val authors: List<String>?,
    val publisher: String?,
    val publishedDate: String?,
) : Parcelable {
    fun allAuthors() : String? {
        return authors?.joinToString(", ")
    }
}

@Parcelize
data class ImageLinks(
    val smallThumbnail: String,
    val thumbnail: String,
) : Parcelable {
    val httpsThumbnail : String
        get() = thumbnail.replace("http", "https")
}

@Parcelize
data class SaleInfo(
    val country: String?,
    val isEbook: Boolean?,
    val listPrice: ListPrice?
) : Parcelable {
    val getPrice2 : String
        get() = "${listPrice?.amount ?: "N/A"} ${listPrice?.currency ?: "N/A"}"
}

@Parcelize
data class ListPrice(
    val amount: Float?,
    val currency: String?
) : Parcelable