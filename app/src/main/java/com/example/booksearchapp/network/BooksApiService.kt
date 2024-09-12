package com.example.booksearchapp.network

import com.example.booksearchapp.model.Book
import com.example.booksearchapp.model.QueryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BooksApiService {

    companion object {
        const val BASE_URL = "https://www.googleapis.com/books/v1/"
    }

    @GET("volumes")
    suspend fun getBooks(@Query("q") query: String): Response<QueryResponse>

    @GET("volumes/{id}")
    suspend fun getBook(@Path("id") id: String): Response<Book>
}