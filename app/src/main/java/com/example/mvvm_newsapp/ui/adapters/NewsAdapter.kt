package com.example.mvvm_newsapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvm_newsapp.databinding.ItemArticlePreviewBinding
import com.example.mvvm_newsapp.models.Article
import com.example.mvvm_newsapp.util.formatDate

class NewsAdapter(
    val onClick: ((Article)->Unit)
): PagingDataAdapter<Article, NewsAdapter.ArticleViewHolder>(ItemComparator()) {

    inner class ArticleViewHolder(private val binding: ItemArticlePreviewBinding ): RecyclerView.ViewHolder(binding.root){
        fun bind(article: Article) = with(binding){
            Glide.with(this.ivArticleImage).load(article.urlToImage).into(ivArticleImage)
            tvSource.text=article.source?.name
            tvTitle.text=article.title
            tvDescription.text=article.description
            val date = article.publishedAt?.let { formatDate(it) }
            tvPublishedAt.text = date
            root.setOnClickListener {
                onClick(article)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticlePreviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class ItemComparator : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url==newItem.url
        }
        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem==newItem
        }
    }
}