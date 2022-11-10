package com.example.demo_sports.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.demo_sports.DATEFROMATFROMAPI
import com.example.demo_sports.EXPECTEDDATEFROMAT
import com.example.demo_sports.databinding.RowPlayersBinding
import com.example.demo_sports.model.Players
import java.text.SimpleDateFormat


class PlayersAdapter: RecyclerView.Adapter<PlayersAdapter.MyViewHolder>() {

    private var items: List<Players>? = null

    fun setsquadPlayerListData(items: List<Players>) {
        this.items = items
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlayersAdapter.MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RowPlayersBinding.inflate(layoutInflater,parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlayersAdapter.MyViewHolder, position: Int) {
        items?.let { data ->
            holder.bind(data[position])
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("loadDateTime")
        fun loadDateTime(txt_date_time: TextView, date_time: String) {
            val dateFormatFromAPI = SimpleDateFormat(DATEFROMATFROMAPI)
            val datetime = dateFormatFromAPI.parse(date_time)
            val dateFormat = SimpleDateFormat(EXPECTEDDATEFROMAT)
            val changedDate = dateFormat.format(datetime)
            txt_date_time.text = changedDate
        }
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    class MyViewHolder(val binding: RowPlayersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Players?) {
            binding.playersData = data
            binding.executePendingBindings()
        }

    }
}