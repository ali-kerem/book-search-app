package com.example.booksearchapp.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.booksearchapp.databinding.FragmentSearchBinding
import com.example.booksearchapp.data.DefaultBooksRepository
import com.example.booksearchapp.network.BooksApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val apiService = Retrofit.Builder()
            .baseUrl(BooksApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BooksApiService::class.java)
        
        val repository = DefaultBooksRepository(apiService)
        
        val factory = SearchViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[SearchViewModel::class.java]

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        /*
        binding.booksRecyclerView.adapter = BooksGridAdapter(BooksGridAdapter.OnClickListener {
            viewModel.getBookDetails(it.id)
        })
         */

        binding.booksRecyclerView.adapter = BooksGridAdapter(BooksGridAdapter.OnClickListener {
            viewModel.getBookDetails(it.id)
        })
    
        binding.searchButton.setOnClickListener {
            val query = binding.searchEditText.text.toString()
            viewModel.updateSearchQuery(query)
            viewModel.searchBooks()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}