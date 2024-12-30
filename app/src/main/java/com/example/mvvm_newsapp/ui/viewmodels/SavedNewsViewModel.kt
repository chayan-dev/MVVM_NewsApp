package com.example.mvvm_newsapp.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mvvm_newsapp.models.Article
import com.example.mvvm_newsapp.repository.NewsRepository
import com.example.mvvm_newsapp.repository.pagingDataSource.NewsPagingSource
import com.example.mvvm_newsapp.repository.pagingDataSource.SearchNewsPagingSource
import com.example.mvvm_newsapp.util.Constants.Companion.PAGE_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedNewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    val savedArticle: MutableLiveData<List<Article>> = MutableLiveData()

    fun getSavedNews() = viewModelScope.launch(Dispatchers.IO) {
        savedArticle.postValue(newsRepository.getSavedNews())
    }

    fun deleteArticle(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        newsRepository.deleteArticle(article)
        getSavedNews()
    }
}