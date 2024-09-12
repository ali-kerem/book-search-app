package com.example.booksearchapp.data

import android.util.Log
import com.example.booksearchapp.model.Book
import com.example.booksearchapp.network.BooksApiService

class DefaultBookshelfRepository(
    private val bookshelfApiService: BooksApiService
) : BooksRepository {
    override suspend fun getBooks(query: String): List<Book>? {
        return try {
            val res = bookshelfApiService.getBooks(query)
            if (res.isSuccessful) {
                Log.d("API_RESPONSE", "getBooks response: ${res.body()}")
                res.body()?.items ?: emptyList()
            } else {
                Log.e("API_RESPONSE", "getBooks error: ${res.errorBody()?.string()}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("API_RESPONSE", "getBooks exception: ${e.message}")
            e.printStackTrace()
            null
        }
    }

    override suspend fun getBook(id: String): Book? {
        return try {
            val res = bookshelfApiService.getBook(id)
            if (res.isSuccessful) {
                Log.d("API_RESPONSE", "getBook response: ${res.body()}")
                res.body()
            } else {
                Log.e("API_RESPONSE", "getBook error: ${res.errorBody()?.string()}")
                null
            }
        } catch (e: Exception) {
            Log.e("API_RESPONSE", "getBook exception: ${e.message}")
            e.printStackTrace()
            null
        }
    }
}