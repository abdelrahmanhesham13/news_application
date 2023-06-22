package com.example.feature.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.base.BaseViewHolder
import com.example.common.loadImage
import com.example.feature.databinding.ListItemNewsBinding
import com.example.feature.model.NewUiModel

class NewViewHolder constructor(
    private val binding: ListItemNewsBinding,
    private val click : ((NewUiModel) -> Unit)? = null

) :
    BaseViewHolder<NewUiModel, ListItemNewsBinding>(binding) {

    init {
        binding.root.setOnClickListener {
            getRowItem()?.let { item ->
                click?.invoke(item)
            }
        }
    }

    override fun bind() {
        getRowItem()?.let {
            binding.newUiModel = it
            binding.newImageView.loadImage(it.urlToImage)
            binding.executePendingBindings()
        }
    }
}