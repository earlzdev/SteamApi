package com.earl.steamapi.presentation.steamGames

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.earl.steamapi.R
import com.earl.steamapi.databinding.FragmentSteamGamesScreenBinding
import com.earl.steamapi.di.AppComponentViewModel
import com.earl.steamapi.domain.SteamApiResponse
import com.earl.steamapi.domain.models.SteamGame
import com.earl.steamapi.presentation.utils.*
import com.earl.steamapi.presentation.utils.Extensions.afterTextChangedDelayed
import kotlinx.coroutines.launch
import javax.inject.Inject

class SteamGamesFragment: BaseFragment<FragmentSteamGamesScreenBinding>(), OnGameClickListener, CoroutinesErrorHandler {

    @Inject
    internal lateinit var steamGamesViewModelFactory: dagger.Lazy<SteamGamesViewModel.Factory>

    private val viewModel: SteamGamesViewModel by viewModels {
        steamGamesViewModelFactory.get()
    }

    override fun onAttach(context: Context) {
        ViewModelProvider(this).get<AppComponentViewModel>()
            .appComponent.inject(this)
        super.onAttach(context)
    }

    override fun viewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentSteamGamesScreenBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initGamesRecyclerAdapter()
        binding.refreshLayout.setOnRefreshListener {
            viewModel.refreshList {
                binding.refreshLayout.isRefreshing = false
            }
        }
    }

    private fun initGamesRecyclerAdapter() {
        val adapter = SteamGamesRecyclerAdapter(this)
        binding.gamesRecycler.adapter = adapter
        if (viewModel.steamGamesStateFlow.value !is SteamApiResponse.Success) {
            viewModel.getSteamGames(this)
        }
        binding.searchEd.afterTextChangedDelayed {
            viewModel.searchGamesByEnteredText(it)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.steamGamesStateFlow
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { response ->
                    when(response) {
                        is SteamApiResponse.Success -> {
                            val newList = response.data.applist.apps
                            adapter.submitList(newList)
                            binding.progressBar.visibility = View.GONE
                            binding.gamesRecycler.visibility = View.VISIBLE
                        }
                        is SteamApiResponse.Failure -> {
                            showErrorAlertDialog(requireContext(), response.errorMessage).show()
                            binding.progressBar.visibility = View.GONE
                            binding.gamesRecycler.visibility = View.VISIBLE
                        }
                        SteamApiResponse.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.gamesRecycler.visibility = View.GONE
                        }
                    }
                }
        }
    binding.gamesRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                KeyboardUtils.forceCloseKeyboard(requireView())
            }
        }
    })
}

override fun onGameClick(item: SteamGame) {
    findNavController().navigate(R.id.action_steamGamesFragment_to_gameDetailsFragment,
        bundleOf(NavArgsKeys.appId to item.appid, NavArgsKeys.appName to item.name))
}

override fun onError(message: String) {
    showErrorAlertDialog(requireContext(), message).show()
}
}