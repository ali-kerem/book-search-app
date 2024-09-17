package com.example.booksearchapp.database

import androidx.room.*

@Dao
interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: BookmarkEntity)

    @Query("SELECT * FROM bookmarks WHERE id = :id")
    suspend fun getBookById(id: String): BookmarkEntity?

    @Query("SELECT * FROM bookmarks")
    suspend fun getAllBooks(): List<BookmarkEntity>

    @Delete
    suspend fun deleteBook(book: BookmarkEntity)
}
