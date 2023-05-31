package com.earl.steamapi.presentation.utils

import android.util.Log
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

class SteamGamesRecyclerAdapter(
    private val clickListener: OnGameClickListener
): ListAdapter<SteamGame, SteamGamesRecyclerAdapter.SteamGameViewHolder>(Diff) {

    companion object Diff: DiffUtil.ItemCallback<SteamGame>() {
        override fun areItemsTheSame(oldItem: SteamGame, newItem: SteamGame) = oldItem.same(newItem)
        override fun areContentsTheSame(oldItem: SteamGame, newItem: SteamGame) = oldItem.equals(newItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SteamGameViewHolder {
        val binding = RecyclerGameItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SteamGameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SteamGameViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            clickListener.onGameClick(item)
        }
    }

    fun filterList(text: String) {
        val newList = currentList.filter {
            it.appid.toString().contains(text) || it.name.contains(text)
        }
        Log.d("tag", "filterList: new list in adapter -> $newList")
        submitList(emptyList())
        submitList(newList)
        notifyDataSetChanged()
    }

    class SteamGameViewHolder(private val binding: RecyclerGameItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SteamGame) {
            val context = binding.gameId.context
            binding.gameId.text = String.format(context.getString(R.string.game_id_s), item.appid)
            binding.gameTitle.text = String.format(context.getString(R.string.game_name_s), item.name)
        }
    }
}