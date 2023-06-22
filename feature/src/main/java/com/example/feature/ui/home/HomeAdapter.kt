package com.example.feature.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.base.BaseRecyclerAdapter
import com.example.common.loadImage
import com.example.feature.databinding.ListItemNewsBinding
import com.example.feature.model.NewUiModel

class HomeAdapter(
    private val clickFunc : ((NewUiModel) -> Unit)? = null
) : BaseRecyclerAdapter<NewUiModel, ListItemNewsBinding, NewViewHolder>(NewItemDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewViewHolder {
        val binding = ListItemNewsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )


        return NewViewHolder(binding = binding, click = clickFunc)
    }

    class NewItemDiffUtil : DiffUtil.ItemCallback<NewUiModel>() {
        override fun areItemsTheSame(oldItem: NewUiModel, newItem: NewUiModel): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: NewUiModel, newItem: NewUiModel): Boolean {
            return oldItem == newItem
        }
    }


}