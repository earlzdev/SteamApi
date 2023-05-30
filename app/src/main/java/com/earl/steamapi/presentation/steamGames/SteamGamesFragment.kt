package com.earl.steamapi.presentation.steamGames

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.earl.steamapi.R
import com.earl.steamapi.databinding.FragmentSteamGamesScreenBinding
import com.earl.steamapi.di.AppComponentViewModel
import com.earl.steamapi.domain.SteamApiResponse
import com.earl.steamapi.domain.models.SteamGame
import com.earl.steamapi.presentation.utils.*
import com.earl.steamapi.presentation.utils.Extensions.afterTextChangedDelayed
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
            if (it.isNotBlank()) {
                viewModel.searchGamesByEnteredText(it) {
                    adapter.submitList(it)
                }
            }
        }
        viewModel.steamGamesStateFlow.onEach { response ->
            when(response) {
                is SteamApiResponse.Success -> {
                    adapter.submitList(response.data.applist.apps)
                    binding.progressBar.visibility = View.GONE
                    binding.gamesRecycler.visibility = View.VISIBLE
                }
                is SteamApiResponse.Failure -> {
                    showErrorAlertDialog(requireContext(), response.errorMessage)
                    binding.progressBar.visibility = View.GONE
                    binding.gamesRecycler.visibility = View.VISIBLE
                }
                SteamApiResponse.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.gamesRecycler.visibility = View.GONE
                }
            }
        }.launchIn(lifecycleScope)
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
        showErrorAlertDialog(requireContext(), message)
    }
}