package com.example.booksearchapp.data

import com.example.booksearchapp.database.BookmarkEntity
import com.example.booksearchapp.model.Book

interface BooksRepository {
    // Network requests
    suspend fun getBooks(query: String): List<Book>?
    suspend fun getBook(id: String): Book?

    // Database operations
    suspend fun insertBook(book: Book)
    suspend fun getBookFromDatabase(id: String): Book?
    suspend fun getAllBooksFromDatabase(): List<Book>
    suspend fun deleteBook(book: Book)
}
