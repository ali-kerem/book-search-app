package com.example.booksearchapp.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.booksearchapp.databinding.FragmentSearchBinding
import com.example.booksearchapp.data.DefaultBooksRepository
import com.example.booksearchapp.data.ServiceLocator
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

        requireActivity().title = "Search Books"

        // Use the singleton repository
        val repository = ServiceLocator.repository

        // Initialize the ViewModel using the singleton repository
        val factory = SearchViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[SearchViewModel::class.java]

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.booksRecyclerView.adapter = BooksGridAdapter(BooksGridAdapter.OnClickListener {
            viewModel.getBookDetails(it.id)
        })

        binding.searchButton.setOnClickListener {
            val query = binding.searchEditText.text.toString()
            viewModel.updateSearchQuery(query)
            viewModel.searchBooks()
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.selectedBook.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToDetailFragment(it))
                viewModel.displayBookDetailsComplete()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
