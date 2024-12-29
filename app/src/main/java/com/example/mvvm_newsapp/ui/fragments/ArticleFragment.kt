package com.example.mvvm_newsapp.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.mvvm_newsapp.R
import com.example.mvvm_newsapp.databinding.FragmentArticleBinding
import com.example.mvvm_newsapp.ui.NewsActivity
import com.example.mvvm_newsapp.ui.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class ArticleFragment: Fragment(R.layout.fragment_article)  {

    private lateinit var binding:FragmentArticleBinding

    lateinit var viewModel: NewsViewModel
    val args: ArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentArticleBinding.bind(view)

        viewModel = (activity as NewsActivity).viewModel
        val article =args.article
        binding.webView.apply {
            webViewClient= WebViewClient()
            loadUrl(article.url.toString())
        }

        binding.fab.setOnClickListener{
            viewModel.saveArticle(article)
            Snackbar.make(view,"Article saved successfully", Snackbar.LENGTH_SHORT).show()
        }
    }
}