package com.example.newsapp_latest.di

import android.content.Context
import androidx.room.Room
import com.example.mvvm_newsapp.db.ArticleDao
import com.example.mvvm_newsapp.db.ArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideNewsDatabase(@ApplicationContext context: Context): ArticleDatabase {
        return Room.databaseBuilder(context, ArticleDatabase::class.java, "NewsDB").build()
    }

    @Singleton
    @Provides
    fun providesNewsDAO(db: ArticleDatabase): ArticleDao
    {
        return db.getArticleDao()
    }
}