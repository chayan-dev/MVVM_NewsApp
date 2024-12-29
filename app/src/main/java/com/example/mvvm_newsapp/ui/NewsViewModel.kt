package com.example.mvvm_newsapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mvvm_newsapp.models.Article
import com.example.mvvm_newsapp.models.NewsResponse
import com.example.mvvm_newsapp.repository.NewsRepository
import com.example.mvvm_newsapp.util.Constants.Companion.PAGE_SIZE
import com.example.mvvm_newsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    val newsRepository: NewsRepository
): ViewModel(){

    val breakingNews: MutableLiveData<PagingData<Article>> = MutableLiveData()

    val searchNews:MutableLiveData<PagingData<Article>> = MutableLiveData()
    var searchNewsPage=1
    var searchNewsResponse:NewsResponse?=null

    val savedArticle: MutableLiveData<PagingData<Article>> = MutableLiveData()


    fun getBreakingNews(countryCode:String)=viewModelScope.launch(Dispatchers.IO) {
        Pager(
            PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                NewsPagingSource(
                    newsRepository,
                    countryCode
                )
            }
        ).flow.cachedIn(viewModelScope).collectLatest { pagingData ->
            breakingNews.postValue(pagingData)
        }
    }

    fun searchNews(searchQuery:String) = viewModelScope.launch(Dispatchers.IO) {
        Pager(
            PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                SearchNewsPagingSource(
                    newsRepository,
                    searchQuery
                )
            }
        ).flow.cachedIn(viewModelScope).collectLatest { pagingData ->
            searchNews.postValue(pagingData)
        }
    }

//    fun searchNews(searchQuery:String)= viewModelScope.launch{
//        searchNews.postValue(Resource.Loading())
//        val response=newsRepository.searchNews(searchQuery,searchNewsPage)
//        searchNews.postValue(handleSearchNewsResponse(response))
//
//    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>):Resource<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
                searchNewsPage++
                if(searchNewsResponse==null) {
                    searchNewsResponse = resultResponse
                }
                else{
                    val oldArticles=searchNewsResponse?.articles
                    val newArticle=resultResponse.articles
                    oldArticles?.addAll(newArticle)
                }
                return Resource.Success(searchNewsResponse?:resultResponse)
            }
        }
        return Resource.Error(response.message())

    }

    fun saveArticle(article: Article)=viewModelScope.launch(Dispatchers.IO) {
        newsRepository.upsert(article)
    }

    fun getSavedNews() = newsRepository.getSavedNews()

    fun deleteArticle(article: Article)=viewModelScope.launch(Dispatchers.IO) {
        newsRepository.deleteArticle(article)
    }


}