package com.example.mvvm_newsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.mvvm_newsapp.R
import com.example.mvvm_newsapp.ui.adapters.NewsAdapter
import com.example.mvvm_newsapp.databinding.FragmentSearchNewsBinding
import com.example.mvvm_newsapp.ui.NewsViewModel
import com.example.mvvm_newsapp.util.Constants.Companion.SEARCH_NEWS_TIME_DELAY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchNewsFragment : Fragment() {

    private lateinit var binding: FragmentSearchNewsBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private var job: Job? = null;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[NewsViewModel::class.java]
        setupRecycleView()
        addObserver()
        addTextChangeListener()
    }

    private fun addObserver() {
        viewModel.searchNews.observe(viewLifecycleOwner) { response ->
            newsAdapter.submitData(lifecycle, response)
        }
    }

    private fun setupRecycleView() {
        newsAdapter = NewsAdapter() { article ->
            val bundle = Bundle().apply {
                putSerializable("article", article)
            }
            findNavController().navigate(
                R.id.action_searchNewsFragment_to_articleFragment,
                bundle
            )
        }
        binding.rvSearchNews.adapter = newsAdapter
        newsAdapter.addLoadStateListener { loadState ->
            if (loadState.source.refresh is LoadState.Loading) showProgressBar()
            else hideProgressBar()
        }
    }

    private fun addTextChangeListener(){
        binding.etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        viewModel.searchNews(editable.toString())
                    }
                }
            }
        }
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }
}