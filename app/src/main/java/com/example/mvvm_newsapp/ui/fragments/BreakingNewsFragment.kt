package com.example.mvvm_newsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.mvvm_newsapp.R
import com.example.mvvm_newsapp.ui.adapters.NewsAdapter
import com.example.mvvm_newsapp.databinding.FragmentBreakingNewsBinding
import com.example.mvvm_newsapp.ui.viewmodels.BreakingNewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    private lateinit var binding: FragmentBreakingNewsBinding
    private lateinit var viewModel: BreakingNewsViewModel
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBreakingNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[BreakingNewsViewModel::class.java]
        setupRecycleView()
        addObservers()
        viewModel.getBreakingNews("us")
    }

    private fun addObservers() {
        viewModel.breakingNews.observe(viewLifecycleOwner) { response ->
            newsAdapter.submitData(lifecycle, response)
        }
    }

    private fun setupRecycleView() {
        newsAdapter = NewsAdapter() { article ->
            val bundle = Bundle().apply {
                putSerializable("article", article)
            }
            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleFragment,
                bundle
            )
        }
        binding.rvBreakingNews.adapter = newsAdapter

        newsAdapter.addLoadStateListener { loadState ->
            if (loadState.source.refresh is LoadState.Loading) showProgressBar()
            else hideProgressBar()
        }
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }

}





