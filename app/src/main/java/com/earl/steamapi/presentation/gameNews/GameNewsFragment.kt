package com.earl.steamapi.presentation.gameNews

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import com.earl.steamapi.R
import com.earl.steamapi.databinding.FragmentGameNewsScreenBinding
import com.earl.steamapi.di.AppComponentViewModel
import com.earl.steamapi.domain.SteamApiResponse
import com.earl.steamapi.domain.models.GameNewsDetails
import com.earl.steamapi.presentation.utils.*
import javax.inject.Inject

class GameNewsFragment: BaseFragment<FragmentGameNewsScreenBinding>(),
    OnGameNewsClickListener, CoroutinesErrorHandler {

    @Inject
    internal lateinit var gameDetailsViewModelFactory: dagger.Lazy<GameNewsViewModel.Factory>

    private val viewModel: GameNewsViewModel by activityViewModels {
        gameDetailsViewModelFactory.get()
    }

    override fun viewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentGameNewsScreenBinding.inflate(inflater, container, false)

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
        binding.refreshLayout.setOnRefreshListener {
            viewModel.refreshList(getAppId() ?: -1) {
                binding.refreshLayout.isRefreshing = false
            }
        }
    }

    private fun initNewsForApp() {
        val adapter = GameNewsRecyclerAdapter(this)
        binding.newsRecycler.adapter = adapter
        viewModel.fetchNewsForGame(getAppId() ?: -1, this)
        viewModel.observeGameNewsLiveData(this) { response ->
            when(response) {
                is SteamApiResponse.Success -> {
                    val list = response.data.appnews.newsitems
                    if (list.isEmpty()) {
                        binding.progressBar.visibility = View.GONE
                        binding.emptyNewsList.visibility = View.VISIBLE
                    } else {
                        binding.progressBar.visibility = View.GONE
                        binding.emptyNewsList.visibility = View.GONE
                        binding.newsRecycler.visibility = View.VISIBLE
                        adapter.submitList(response.data.appnews.newsitems)
                    }
                }
                is SteamApiResponse.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    binding.newsRecycler.visibility = View.GONE
                    binding.emptyNewsList.visibility = View.VISIBLE
                }
                SteamApiResponse.Loading -> {
                    binding.newsRecycler.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                    binding.emptyNewsList.visibility = View.GONE
                }
            }
        }
    }

    private fun getAppId() = arguments?.let {
        binding.header.text = String.format(
            requireContext().getString(R.string.details_of_game_s),
            it.getString(NavArgsKeys.appName) ?: getString(R.string.no_title)
        )
        it.getInt(NavArgsKeys.appId)
    }

    override fun onGameNewsClick(item: GameNewsDetails) {
        findNavController().navigate(R.id.action_gameDetailsFragment_to_gameNewsFragment, bundleOf(NavArgsKeys.newsId to item.gid))
    }

    override fun onError(message: String) {
        showErrorAlertDialog(requireContext(), message).show()
    }
}