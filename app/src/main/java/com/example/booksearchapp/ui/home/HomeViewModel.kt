package com.example.booksearchapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booksearchapp.data.BooksRepository
import com.example.booksearchapp.model.Book
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: BooksRepository) : ViewModel() {

    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> = _books

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _isSearchMode = MutableLiveData<Boolean>(false)
    val isSearchMode: LiveData<Boolean> = _isSearchMode

    init {
        loadBookmarks()
    }

    fun loadBookmarks() {
        viewModelScope.launch {
            _books.value = repository.getAllBooksFromDatabase()
            _isSearchMode.value = false
        }
    }

    fun searchBooks(query: String) {
        viewModelScope.launch {
            try {
                val searchResults = repository.getBooks(query)
                _books.value = searchResults ?: emptyList()
                _isSearchMode.value = true
            } catch (e: Exception) {
                _errorMessage.value = "Error searching books: ${e.message}"
            }
        }
    }

    fun backToBookmarks() {
    if (_isSearchMode.value == true) {
        loadBookmarks()
        _isSearchMode.value = false
    }
}
}