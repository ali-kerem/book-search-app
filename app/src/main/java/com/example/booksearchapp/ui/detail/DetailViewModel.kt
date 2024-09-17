package com.example.booksearchapp.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.booksearchapp.data.BooksRepository
import com.example.booksearchapp.model.Book
import kotlinx.coroutines.launch

class DetailViewModel(
    book: Book,
    app: Application,
    private val repository: BooksRepository
) : AndroidViewModel(app) {
    private val _selectedBook = MutableLiveData<Book>()
    val selectedBook: LiveData<Book> = _selectedBook

    private val _bookmarkStatus = MutableLiveData<Boolean>()
    val bookmarkStatus: LiveData<Boolean> = _bookmarkStatus

    init {
        _selectedBook.value = book
        checkBookmarkStatus()
    }

    private fun checkBookmarkStatus() {
        viewModelScope.launch {
            val bookmarkedBook = repository.getBookFromDatabase(_selectedBook.value?.id ?: "")
            _bookmarkStatus.value = bookmarkedBook != null
        }
    }

    fun toggleBookmark() {
        viewModelScope.launch {
            val currentBook = _selectedBook.value ?: return@launch
            if (_bookmarkStatus.value == true) {
                repository.deleteBook(currentBook)
            } else {
                repository.insertBook(currentBook)
            }
            checkBookmarkStatus()
        }
    }
}