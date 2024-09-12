package com.example.booksearchapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booksearchapp.data.BooksRepository
import com.example.booksearchapp.model.Book
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModelProvider

enum class ApiStatus { LOADING, ERROR, DONE }

class SearchViewModel(private val repository: BooksRepository) : ViewModel() {

    private val _searchBooksResponse = MutableLiveData<List<Book>?>()
    val searchBooksResponse: LiveData<List<Book>?> = _searchBooksResponse

    val searchQuery = MutableLiveData<String>()

    private val _selectedBook = MutableLiveData<Book?>()
    val selectedBook: LiveData<Book?> = _selectedBook

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status

    fun updateSearchQuery(query: String) {
        searchQuery.value = query
    }

    fun searchBooks() = viewModelScope.launch {
        _status.value = ApiStatus.LOADING
        try {
            val query = searchQuery.value ?: return@launch
            val books = repository.getBooks(query)
            _searchBooksResponse.value = books
            _status.value = ApiStatus.DONE
        } catch (e: Exception) {
            _status.value = ApiStatus.ERROR
            _searchBooksResponse.value = emptyList()
        }
    }

    fun getBookDetails(bookId: String) = viewModelScope.launch {
        val book = repository.getBook(bookId)
        _selectedBook.value = book
    }
}

class SearchViewModelFactory(private val repository: BooksRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}