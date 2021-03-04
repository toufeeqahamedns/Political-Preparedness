package com.example.android.politicalpreparedness.election.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.databinding.ElectionListItemBinding
import com.example.android.politicalpreparedness.network.models.Election

class ElectionListAdapter(private val clickListener: ElectionListener) : ListAdapter<Election, ElectionViewHolder>(ElectionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElectionViewHolder {
        return ElectionViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ElectionViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }
}

class ElectionViewHolder(private val electionListItemViewBinding: ElectionListItemBinding) :
        RecyclerView.ViewHolder(electionListItemViewBinding.root) {

    fun bind(item: Election, clickListener: ElectionListener) {
        electionListItemViewBinding.election = item
        electionListItemViewBinding.executePendingBindings()

        electionListItemViewBinding.root.setOnClickListener {
            clickListener.onClick(item)
        }
    }

    companion object {
        fun from(parent: ViewGroup): ElectionViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ElectionListItemBinding.inflate(layoutInflater, parent, false)
            return ElectionViewHolder(binding)
        }
    }
}

class ElectionDiffCallback : DiffUtil.ItemCallback<Election>() {
    override fun areItemsTheSame(oldItem: Election, newItem: Election): Boolean =
            (oldItem.id == newItem.id)

    override fun areContentsTheSame(oldItem: Election, newItem: Election): Boolean = true
}

class ElectionListener(private val clickListener: (election: Election) -> Unit) {
    fun onClick(election: Election) = clickListener(election)
}