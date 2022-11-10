package com.example.demo_sports.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.demo_sports.databinding.RowMatchBinding
import com.example.demo_sports.model.ResponseFromApi
import com.example.demo_sports.ui.fragment.SquadFragmentDirections

class MatchListAdapter : RecyclerView.Adapter<MatchListAdapter.MyViewHolder>() {

    var items: List<ResponseFromApi>? = null

    fun setRepoListData(items: List<ResponseFromApi>) {
        this.items = items
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MatchListAdapter.MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RowMatchBinding.inflate(layoutInflater,parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MatchListAdapter.MyViewHolder, position: Int) {
        items?.let { data ->
            holder.bind(data[position])
            holder.binding.cardView.setOnClickListener {
                val action = SquadFragmentDirections.actionSquadFragmentToSquadDetailsFragment(
                    data[position]
                )
                holder.binding.root.findNavController().navigate(action)
            }
        }
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    class MyViewHolder(val binding: RowMatchBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ResponseFromApi) {
            binding.matchData = data
            binding.executePendingBindings()
        }
    }
}