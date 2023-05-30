package com.earl.steamapi.presentation.gameDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import com.earl.steamapi.databinding.FragmentGameDetailsScreenBinding
import com.earl.steamapi.presentation.utils.BaseFragment

class GameDetailsFragment: BaseFragment<FragmentGameDetailsScreenBinding>() {

    override fun viewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentGameDetailsScreenBinding.inflate(inflater, container, false)
}