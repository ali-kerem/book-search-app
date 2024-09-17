package com.example.booksearchapp.ui.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.booksearchapp.data.BooksRepository
import com.example.booksearchapp.model.Book

class DetailViewModelFactory(
    private val book: Book,
    private val application: Application,
    private val repository: BooksRepository
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(book, application, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}