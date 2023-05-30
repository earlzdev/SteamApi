package com.earl.steamapi.presentation.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.earl.steamapi.R
import com.earl.steamapi.databinding.RecyclerGameNewsItemBinding
import com.earl.steamapi.domain.models.GameNewsDetails

interface OnGameNewsClickListener {

    fun onGameNewsClick(item: GameNewsDetails)
}

class GameNewsRecyclerAdapter(
    private val clickListener: OnGameNewsClickListener
): ListAdapter<GameNewsDetails, GameNewsRecyclerAdapter.GameNewsViewHolder>(Diff) {

    private companion object Diff: DiffUtil.ItemCallback<GameNewsDetails>() {
        override fun areItemsTheSame(oldItem: GameNewsDetails, newItem: GameNewsDetails) = oldItem.same(newItem)
        override fun areContentsTheSame(oldItem: GameNewsDetails, newItem: GameNewsDetails) = oldItem.equals(newItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameNewsViewHolder {
        val binding = RecyclerGameNewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameNewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameNewsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            clickListener.onGameNewsClick(item)
        }
    }

    class GameNewsViewHolder(private val binding: RecyclerGameNewsItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GameNewsDetails) {
            val context = binding.author.context
            binding.author.text = String.format(context.getString(R.string.author_s), item.author)
            binding.feedLable.text = String.format(context.getString(R.string.feed_label_s), item.feedLabel)
            binding.header.text = String.format(context.getString(R.string.header_s), item.title)
        }
    }
}