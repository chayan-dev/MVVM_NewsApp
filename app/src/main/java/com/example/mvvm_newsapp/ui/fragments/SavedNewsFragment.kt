package com.example.mvvm_newsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_newsapp.R
import com.example.mvvm_newsapp.adapters.NewsAdapter
import com.example.mvvm_newsapp.databinding.FragmentSavedNewsBinding
import com.example.mvvm_newsapp.ui.NewsActivity
import com.example.mvvm_newsapp.ui.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedNewsFragment: Fragment() {

    private lateinit var binding:FragmentSavedNewsBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentSavedNewsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[NewsViewModel::class.java]
        setupRecycleView()
        addSwipeDelete(view)

        viewModel.savedArticle.observe(viewLifecycleOwner){ articles ->
            newsAdapter.submitData(lifecycle,articles)
        }


        viewModel.getSavedNews()
    }

    private fun setupRecycleView(){
        newsAdapter= NewsAdapter(){ article ->
            val bundle=Bundle().apply {
                putSerializable("article",article)
            }
            findNavController().navigate(
                R.id.action_savedNewsFragment_to_articleFragment,
                bundle
            )
        }
        binding.rvSavedNews.adapter=newsAdapter
    }

    private fun addSwipeDelete(view: View){
        val itemTouchHelperCallBack = object: ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position=viewHolder.bindingAdapterPosition
                val article=newsAdapter.peek(position)
                if (article != null) {
                    viewModel.deleteArticle(article)
                    Snackbar.make(view,"Successfully deleted article", Snackbar.LENGTH_LONG).apply {
                        setAction("Undo"){
                            viewModel.saveArticle(article)
                        }
                        show()
                    }
                }

            }
        }

        ItemTouchHelper(itemTouchHelperCallBack).apply {
            attachToRecyclerView(binding.rvSavedNews)
        }
    }
}