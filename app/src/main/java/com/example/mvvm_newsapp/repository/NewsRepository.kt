package com.example.mvvm_newsapp.repository

import com.example.mvvm_newsapp.api.NewsAPI
import com.example.mvvm_newsapp.db.ArticleDao
import com.example.mvvm_newsapp.db.ArticleDatabase
import com.example.mvvm_newsapp.models.Article
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val api: NewsAPI,
    private val dao: ArticleDao
) {

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        api.searchForNews(searchQuery, pageNumber)

    suspend fun upsert(article: Article) = dao.upsert(article)

    suspend fun getSavedNews() = dao.getAllArticles()

    suspend fun deleteArticle(article: Article) = dao.deleteArticle(article)


}