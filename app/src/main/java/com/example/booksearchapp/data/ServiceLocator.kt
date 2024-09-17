package com.example.booksearchapp.data

import android.content.Context
import com.example.booksearchapp.database.BookmarkDao
import com.example.booksearchapp.database.BookmarksDatabase
import com.example.booksearchapp.network.BooksApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceLocator {

    private val apiService: BooksApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BooksApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BooksApiService::class.java)
    }

    private lateinit var bookDao: BookmarkDao

    val repository: BooksRepository by lazy {
        DefaultBooksRepository(apiService, bookDao)
    }

    fun init(context: Context) {
        val database = BookmarksDatabase.getDatabase(context)
        bookDao = database.bookDao()
    }
}
