package com.example.booksearchapp.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.booksearchapp.databinding.GridBookItemBinding
import com.example.booksearchapp.model.Book

class BooksGridAdapter( val onClickListener: OnClickListener ) :
    ListAdapter<Book, BooksGridAdapter.BookViewHolder>(DiffCallback) {
    class BookViewHolder(private var binding: GridBookItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Book) {
            binding.book = book
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): BookViewHolder {
        return BookViewHolder(GridBookItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(book)
        }
        holder.bind(book)
    }

    class OnClickListener(val clickListener: (book: Book) -> Unit) {
        fun onClick(book:Book) = clickListener(book)
    }
}