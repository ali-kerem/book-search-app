package com.example.booksearchapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.booksearchapp.data.ServiceLocator
import com.example.booksearchapp.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = requireNotNull(activity).application
        val book = args.selectedBook
        val repository = ServiceLocator.repository
        val viewModelFactory = DetailViewModelFactory(book, application, repository)

        viewModel = ViewModelProvider(this, viewModelFactory)[DetailViewModel::class.java]

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        setupBookmarkButton()
    }

    private fun setupBookmarkButton() {
        binding.bookmarkButton.setOnClickListener {
            viewModel.toggleBookmark()
        }

        viewModel.bookmarkStatus.observe(viewLifecycleOwner) { isBookmarked ->
            binding.bookmarkButton.text = if (isBookmarked) "Remove Bookmark" else "Add Bookmark"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
        android.R.id.home -> {
            findNavController().navigateUp()
            true
        }
        else -> super.onOptionsItemSelected(item)
        }
    }
}