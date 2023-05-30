package com.earl.steamapi.presentation.gameNews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.earl.steamapi.databinding.FragmentGameNewsScreenBinding
import com.earl.steamapi.presentation.utils.BaseFragment

class GameNewsFragment: BaseFragment<FragmentGameNewsScreenBinding>() {

    override fun viewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentGameNewsScreenBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}