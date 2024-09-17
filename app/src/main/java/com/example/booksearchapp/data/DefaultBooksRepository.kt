package com.example.booksearchapp.data

import android.util.Log
import com.example.booksearchapp.database.BookmarkDao
import com.example.booksearchapp.database.BookmarkEntity
import com.example.booksearchapp.database.ImageLinksEntity
import com.example.booksearchapp.database.ListPriceEntity
import com.example.booksearchapp.database.SaleInfoEntity
import com.example.booksearchapp.database.VolumeInfoEntity
import com.example.booksearchapp.model.Book
import com.example.booksearchapp.model.ImageLinks
import com.example.booksearchapp.model.ListPrice
import com.example.booksearchapp.model.SaleInfo
import com.example.booksearchapp.model.VolumeInfo
import com.example.booksearchapp.network.BooksApiService

class DefaultBooksRepository(
    private val bookshelfApiService: BooksApiService,
    private val bookDao: BookmarkDao // Inject the BookDao for database interactions
) : BooksRepository {

    // Network request to fetch a list of books
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

    // Network request to fetch a single book by ID
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

    // Insert a book into the local database
    override suspend fun insertBook(book: Book) {
        bookDao.insertBook(mapBookToEntity(book))
    }

    // Retrieve a book from the local database by its ID
    override suspend fun getBookFromDatabase(id: String): Book? {
        return bookDao.getBookById(id)?.let { mapEntityToBook(it) }
    }

    // Retrieve all books from the local database
    override suspend fun getAllBooksFromDatabase(): List<Book> {
        return bookDao.getAllBooks().map { mapEntityToBook(it) }
    }

    // Delete a book from the local database
    override suspend fun deleteBook(book: Book) {
        bookDao.deleteBook(mapBookToEntity(book))
    }

    // Helper function to map API Book model to database BookEntity
    private fun mapBookToEntity(book: Book): BookmarkEntity {
        return BookmarkEntity(
            id = book.id,
            description = book.description,
            volumeInfo = VolumeInfoEntity(
                title = book.volumeInfo.title,
                subtitle = book.volumeInfo.subtitle,
                description = book.volumeInfo.description,
                imageLinks = book.volumeInfo.imageLinks?.let {
                    ImageLinksEntity(
                        smallThumbnail = it.smallThumbnail,
                        thumbnail = it.thumbnail
                    )
                },
                authors = book.volumeInfo.authors,
                publisher = book.volumeInfo.publisher,
                publishedDate = book.volumeInfo.publishedDate
            ),
            saleInfo = book.saleInfo?.let {
                SaleInfoEntity(
                    country = it.country,
                    isEbook = it.isEbook,
                    listPrice = it.listPrice?.let { listPrice ->
                        ListPriceEntity(listPrice.amount, listPrice.currency)
                    }
                )
            }
        )
    }

    private fun mapEntityToBook(bookEntity: BookmarkEntity): Book {
        return Book(
            id = bookEntity.id,
            description = bookEntity.description,
            volumeInfo = VolumeInfo(
                title = bookEntity.volumeInfo.title,
                subtitle = bookEntity.volumeInfo.subtitle,
                description = bookEntity.volumeInfo.description,
                imageLinks = bookEntity.volumeInfo.imageLinks?.let {
                    ImageLinks(
                        smallThumbnail = it.smallThumbnail,
                        thumbnail = it.thumbnail
                    )
                },
                authors = bookEntity.volumeInfo.authors,
                publisher = bookEntity.volumeInfo.publisher,
                publishedDate = bookEntity.volumeInfo.publishedDate
            ),
            saleInfo = bookEntity.saleInfo?.let {
                SaleInfo(
                    country = it.country,
                    isEbook = it.isEbook,
                    listPrice = it.listPrice?.let { listPrice ->
                        ListPrice(listPrice.amount, listPrice.currency)
                    }
                )
            } ?: SaleInfo(
                country = null,
                isEbook = false,
                listPrice = null
            )
        )
    }


}
