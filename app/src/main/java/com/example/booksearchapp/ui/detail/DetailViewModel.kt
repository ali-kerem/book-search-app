package com.example.booksearchapp.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.booksearchapp.model.Book

class DetailViewModel(book: Book, app: Application) : AndroidViewModel(app) {
    private val _selectedBook = MutableLiveData<Book>()

    val selectedBook: LiveData<Book>
        get() = _selectedBook

    init {
        _selectedBook.value = book
    }
}