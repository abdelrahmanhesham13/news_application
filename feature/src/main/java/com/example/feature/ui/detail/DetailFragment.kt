package com.example.feature.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.base.BaseFragment
import com.example.common.loadImage
import com.example.feature.databinding.FragmentDetailBinding
import com.example.feature.databinding.FragmentHomeBinding
import com.example.feature.ui.MainActivity

class DetailFragment: BaseFragment<FragmentDetailBinding>() {

    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailBinding
        get() = FragmentDetailBinding::inflate

    private val args: DetailFragmentArgs by navArgs()

    override fun prepareView(savedInstanceState: Bundle?) {
        binding.newAuthor.text = args.newUiModel.author
        binding.newContent.text = args.newUiModel.content
        binding.newDate.text = args.newUiModel.publishedAt
        binding.newDescription.text = args.newUiModel.description
        binding.newTitle.text = args.newUiModel.title
        binding.newImageView.loadImage(args.newUiModel.urlToImage)
        (requireActivity() as? MainActivity)?.setTitle(args.newUiModel.sourceUiModel.name)
    }

}