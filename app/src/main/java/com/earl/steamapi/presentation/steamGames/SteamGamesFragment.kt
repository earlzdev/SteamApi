package com.earl.steamapi.presentation.steamGames

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.earl.steamapi.databinding.FragmentSteamGamesScreenBinding
import com.earl.steamapi.di.AppComponentViewModel
import com.earl.steamapi.domain.SteamApiResponse
import com.earl.steamapi.domain.models.SteamGame
import com.earl.steamapi.presentation.utils.BaseFragment
import com.earl.steamapi.presentation.utils.CoroutinesErrorHandler
import com.earl.steamapi.presentation.utils.OnGameClickListener
import com.earl.steamapi.presentation.utils.SteamGamesRecyclerAdapter
import javax.inject.Inject

class SteamGamesFragment: BaseFragment<FragmentSteamGamesScreenBinding>(), OnGameClickListener, CoroutinesErrorHandler {

    @Inject
    internal lateinit var searchAirportsViewModelFactory: dagger.Lazy<SteamGamesViewModel.Factory>

    private val viewModel: SteamGamesViewModel by viewModels {
        searchAirportsViewModelFactory.get()
    }

    override fun onAttach(context: Context) {
        ViewModelProvider(this).get<AppComponentViewModel>()
            .newDetailsComponent.inject(this)
        super.onAttach(context)
    }

    override fun viewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentSteamGamesScreenBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initGamesRecyclerAdapter()
    }

    private fun initGamesRecyclerAdapter() {
        val adapter = SteamGamesRecyclerAdapter(this)
        binding.gamesRecycler.adapter = adapter
        viewModel.getSteamGames(this)
        viewModel.observeSteamGameLiveData(this) { response ->
            when(response) {
                is SteamApiResponse.Success -> {
                    adapter.submitList(response.data.applist.apps)
                    binding.progressBar.visibility = View.GONE
                    binding.gamesRecycler.visibility = View.VISIBLE
                }
                is SteamApiResponse.Failure -> {
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

    override fun onGameClick(item: SteamGame) {

    }

    override fun onError(message: String) {

    }
}