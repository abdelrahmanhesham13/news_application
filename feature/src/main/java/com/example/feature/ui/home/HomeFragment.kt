package com.example.feature.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.base.BaseFragment
import com.example.feature.databinding.FragmentHomeBinding
import com.example.feature.ui.MainActivity
import com.example.feature.ui.contract.HomeContract
import com.example.feature.ui.vm.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    private val viewModel: HomeViewModel by viewModels()
    private var adapter: HomeAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setEvent(HomeContract.Event.FetchData)
    }

    override fun prepareView(savedInstanceState: Bundle?) {
        adapter = HomeAdapter {
            viewModel.setEvent(HomeContract.Event.NewSelected(it))
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.setEvent(HomeContract.Event.LoadMoreData)
                }
            }
        })
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.setEvent(HomeContract.Event.OnPullToRefresh)
        }
        initObservers()
    }

    /**
     * Initialize Observers
     */
    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (val state = it.homeState) {
                        is HomeContract.HomeState.Empty -> {
                            binding.swipeRefreshLayout.isRefreshing = false
                            binding.recyclerView.visibility = View.GONE
                            binding.emptyView.visibility = View.VISIBLE
                        }

                        is HomeContract.HomeState.Idle -> {
                            binding.swipeRefreshLayout.isRefreshing = false
                        }

                        is HomeContract.HomeState.Loading -> {
                            binding.swipeRefreshLayout.isRefreshing = true
                        }

                        is HomeContract.HomeState.Success -> {
                            (requireActivity() as? MainActivity)?.setTitle(state.title)
                            adapter?.submitList(state.news.data.toList())
                            binding.swipeRefreshLayout.isRefreshing = false
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.effect.collect {
                    when (it) {
                        is HomeContract.Effect.ShowError -> {
                            binding.swipeRefreshLayout.isRefreshing = false
                            val msg = it.message
                            Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show();
                        }

                        is HomeContract.Effect.NavigateToNewDetails -> {
                            findNavController().navigate(
                                HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                                    it.newUiModel
                                )
                            )
                        }
                    }
                }
            }
        }

    }

    override fun onDestroyView() {
        adapter = null
        super.onDestroyView()
    }

}