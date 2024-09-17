package com.example.booksearchapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [BookmarkEntity::class], version = 3)
@TypeConverters(Converters::class)
abstract class BookmarksDatabase : RoomDatabase() {

    // Define the DAO
    abstract fun bookDao(): BookmarkDao

    companion object {
        @Volatile
        private var INSTANCE: BookmarksDatabase? = null

        // Singleton pattern to ensure only one instance of the database is created
        fun getDatabase(context: Context): BookmarksDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BookmarksDatabase::class.java,
                    "book_database"
                )
                    .fallbackToDestructiveMigration() // Handle schema migrations
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

