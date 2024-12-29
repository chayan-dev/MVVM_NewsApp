package com.example.mvvm_newsapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvm_newsapp.databinding.ItemArticlePreviewBinding
import com.example.mvvm_newsapp.models.Article

class NewsAdapter(
    val onClick: ((Article)->Unit)
): RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(private val binding: ItemArticlePreviewBinding ): RecyclerView.ViewHolder(binding.root){
        fun bind(article: Article) = with(binding){
            Glide.with(this.ivArticleImage).load(article.urlToImage).into(ivArticleImage)
            tvSource.text=article.source?.name
            tvTitle.text=article.title
            tvDescription.text=article.description
            tvPublishedAt.text=article.publishedAt
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
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val differCallback= object: DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url==newItem.url
        }
        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem==newItem
        }
    }

    val differ= AsyncListDiffer(this,differCallback)
}