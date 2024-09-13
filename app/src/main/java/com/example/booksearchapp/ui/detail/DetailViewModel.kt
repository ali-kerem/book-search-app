package com.example.booksearchapp.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.booksearchapp.R
import com.example.booksearchapp.model.Book

class DetailViewModel(book: Book, app: Application) : AndroidViewModel(app) {
    private val _selectedBook = MutableLiveData<Book>()

    // The external LiveData for the SelectedBook
    val selectedBook: LiveData<Book>
        get() = _selectedBook

    // Initialize the _selectedBook MutableLiveData
    init {
        _selectedBook.value = book
    }
}