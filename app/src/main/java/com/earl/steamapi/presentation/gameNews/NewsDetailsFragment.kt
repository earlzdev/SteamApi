package com.earl.steamapi.presentation.gameNews

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import com.earl.steamapi.R
import com.earl.steamapi.databinding.FragmentGameNewsScreenBinding
import com.earl.steamapi.databinding.FragmentNewsDetailsScreenBinding
import com.earl.steamapi.di.AppComponentViewModel
import com.earl.steamapi.presentation.gameNews.GameNewsViewModel
import com.earl.steamapi.presentation.utils.BaseFragment
import com.earl.steamapi.presentation.utils.NavArgsKeys
import javax.inject.Inject

class NewsDetailsFragment: BaseFragment<FragmentNewsDetailsScreenBinding>() {

    @Inject
    internal lateinit var gameDetailsViewModelFactory: dagger.Lazy<GameNewsViewModel.Factory>

    private val viewModel: GameNewsViewModel by activityViewModels {
        gameDetailsViewModelFactory.get()
    }

    override fun viewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentNewsDetailsScreenBinding.inflate(inflater, container, false)

    override fun onAttach(context: Context) {
        ViewModelProvider(this).get<AppComponentViewModel>()
            .appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNewsDetails()
        binding.backBtn.setOnClickListener { 
            findNavController().popBackStack()
        }
    }

    private fun initNewsDetails() {
        viewModel.findNewsById(getNewsId() ?: "")?.let { newsDetails ->
            binding.contents.text = String.format(requireContext().getString(R.string.contents_s), newsDetails.contents)
            binding.url.text = newsDetails.url
            binding.newsHeader.text = String.format(requireContext().getString(R.string.title_s), newsDetails.title)
        }
    }

    private fun getNewsId() = arguments?.let {
        it.getString(NavArgsKeys.newsId)
    }
}