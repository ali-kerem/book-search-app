package com.example.booksearchapp.ui.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.booksearchapp.data.ServiceLocator
import com.example.booksearchapp.databinding.FragmentBookmarksBinding
import com.example.booksearchapp.ui.search.BooksGridAdapter

class BookmarksFragment : Fragment() {

    private var _binding: FragmentBookmarksBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: BookmarksViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().title = "Bookmarks"

        val repository = ServiceLocator.repository
        val factory = BookmarksViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[BookmarksViewModel::class.java]

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val adapter = BooksGridAdapter(BooksGridAdapter.OnClickListener {
            findNavController().navigate(
                BookmarksFragmentDirections.actionBookmarksFragmentToDetailFragment(it)
            )
        })

        binding.bookmarksRecyclerView.adapter = adapter

        viewModel.bookmarks.observe(viewLifecycleOwner) { bookmarks ->
            adapter.submitList(bookmarks)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadBookmarks()
    }
}