package com.example.booksearchapp.ui.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booksearchapp.data.BooksRepository
import com.example.booksearchapp.model.Book
import kotlinx.coroutines.launch

class BookmarksViewModel(private val repository: BooksRepository) : ViewModel() {

    private val _bookmarks = MutableLiveData<List<Book>>()
    val bookmarks: LiveData<List<Book>> = _bookmarks

    init {
        loadBookmarks()
    }

    fun loadBookmarks() {
        viewModelScope.launch {
            _bookmarks.value = repository.getAllBooksFromDatabase()
        }
    }

    fun deleteBookmark(book: Book) {
        viewModelScope.launch {
            repository.deleteBook(book)
            loadBookmarks() // Reload bookmarks after deletion
        }
    }
}