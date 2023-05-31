package com.earl.steamapi.presentation.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.earl.steamapi.R
import com.earl.steamapi.databinding.RecyclerGameItemBinding
import com.earl.steamapi.domain.models.SteamGame

interface OnGameClickListener {

    fun onGameClick(item: SteamGame)
}

class SteamGamesRecycler(
    private val clickListener: OnGameClickListener
): RecyclerView.Adapter<SteamGamesRecycler.SteamGameHolder>() {

    var data: List<SteamGame> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SteamGameHolder {
        val binding = RecyclerGameItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SteamGameHolder(binding)
    }

    override fun onBindViewHolder(holder: SteamGameHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            clickListener.onGameClick(item)
        }
    }

    override fun getItemCount() = data.size

    class SteamGameHolder(private val binding: RecyclerGameItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SteamGame) {
            val context = binding.gameId.context
            binding.gameId.text = String.format(context.getString(R.string.game_id_s), item.appid)
            binding.gameTitle.text = String.format(context.getString(R.string.game_name_s), item.name)
        }
    }
}