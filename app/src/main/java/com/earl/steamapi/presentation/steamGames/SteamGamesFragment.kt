package com.earl.steamapi.presentation.steamGames

import android.view.LayoutInflater
import android.view.ViewGroup
import com.earl.steamapi.databinding.FragmentSteamGamesScreenBinding
import com.earl.steamapi.presentation.utils.BaseFragment

class SteamGamesFragment: BaseFragment<FragmentSteamGamesScreenBinding>() {

    override fun viewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentSteamGamesScreenBinding.inflate(inflater, container, false)
}