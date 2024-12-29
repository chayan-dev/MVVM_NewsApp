package com.example.mvvm_newsapp.ui

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mvvm_newsapp.models.Article
import com.example.mvvm_newsapp.repository.NewsRepository


class NewsPagingSource(
    private val repository: NewsRepository,
    private val countryCode: String
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1 // Start from page 1
        return try {
            val response = repository.getBreakingNews(countryCode, page)
            if (response.isSuccessful) {
                val articles = response.body()?.articles ?: emptyList()
                LoadResult.Page(
                    data = articles,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (articles.isEmpty()) null else page + 1
                )
            } else {
                LoadResult.Error(Throwable(response.message()))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}