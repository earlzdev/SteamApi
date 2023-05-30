package com.earl.steamapi.presentation.gameNews

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import com.earl.steamapi.R
import com.earl.steamapi.databinding.FragmentGameDetailsScreenBinding
import com.earl.steamapi.di.AppComponentViewModel
import com.earl.steamapi.domain.SteamApiResponse
import com.earl.steamapi.domain.models.GameNewsDetails
import com.earl.steamapi.presentation.utils.*
import javax.inject.Inject

class GameNewsFragment: BaseFragment<FragmentGameDetailsScreenBinding>(),
    OnGameNewsClickListener, CoroutinesErrorHandler {

    @Inject
    internal lateinit var gameDetailsViewModelFactory: dagger.Lazy<GameNewsViewModel.Factory>

    private val viewModel: GameNewsViewModel by viewModels {
        gameDetailsViewModelFactory.get()
    }

    override fun viewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentGameDetailsScreenBinding.inflate(inflater, container, false)

    override fun onAttach(context: Context) {
        ViewModelProvider(this).get<AppComponentViewModel>()
            .appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNewsForApp()
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initNewsForApp() {
        val adapter = GameNewsRecyclerAdapter(this)
        binding.newsRecycler.adapter = adapter
        viewModel.fetchNewsForGame(getAppId() ?: -1, this)
        viewModel.observeGameNewsLiveData(this) { response ->
            when(response) {
                is SteamApiResponse.Success -> {
                    if (response.data.appnews.newsitems.isEmpty()) {
                        binding.emotyList.visibility = View.VISIBLE
                    } else {
                        binding.emotyList.visibility = View.GONE
                        adapter.submitList(response.data.appnews.newsitems)
                    }
                    binding.progressBar.visibility = View.GONE
                    binding.newsRecycler.visibility = View.VISIBLE
                }
                is SteamApiResponse.Failure -> {
                    showErrorAlertDialog(requireContext(), response.errorMessage)
                    binding.progressBar.visibility = View.GONE
                    binding.newsRecycler.visibility = View.VISIBLE
                    binding.emotyList.visibility = View.GONE
                }
                SteamApiResponse.Loading -> {
                    binding.emotyList.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                    binding.newsRecycler.visibility = View.GONE
                }
            }
        }
    }

    private fun getAppId() = arguments?.let {
        binding.header.text = String.format(requireContext().getString(R.string.details_of_game_s), it.getString(NavArgsKeys.appName))
        it.getInt(NavArgsKeys.appId)
    }

    override fun onGameNewsClick(item: GameNewsDetails) {

    }

    override fun onError(message: String) {
        showErrorAlertDialog(requireContext(), message)
    }
}