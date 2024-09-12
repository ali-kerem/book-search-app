package com.example.booksearchapp.data

import com.example.booksearchapp.model.Book

interface BooksRepository {
    suspend fun getBooks(query: String): List<Book>?

    suspend fun getBook(id: String): Book?
}