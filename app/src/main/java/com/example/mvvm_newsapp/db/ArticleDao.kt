package com.example.mvvm_newsapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mvvm_newsapp.models.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(article: Article): Long

    @Query("SELECT * FROM articles")
    fun getAllArticles(): List<Article>

    @Delete
    fun deleteArticle(article: Article)
}