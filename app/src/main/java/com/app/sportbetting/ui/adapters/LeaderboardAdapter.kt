package com.app.sportbetting.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.sportbetting.databinding.LeaderboardItemsBinding
import com.app.sportbetting.models.leaderboard.LeaderboardModel
import java.util.ArrayList

class LeaderboardAdapter(context: Context,) : RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder>() {

    private var list = emptyList<LeaderboardModel>()
    inner class LeaderboardViewHolder(val binding :LeaderboardItemsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderboardViewHolder {
        val binding = LeaderboardItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LeaderboardViewHolder(binding)
    }


    override fun onBindViewHolder(holder: LeaderboardViewHolder, position: Int) {
        holder.binding.ptsValue.text = list[position].points.toString()
        holder.binding.marValue.text = list[position].mar.toString()
        holder.binding.profileName.text = list[position].name
    }

    fun setData(list: ArrayList<LeaderboardModel>) {
        this.list = list
    }


    override fun getItemCount(): Int {
        return list.size
    }

}