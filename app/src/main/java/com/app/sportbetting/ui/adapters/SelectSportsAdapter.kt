package com.app.sportbetting.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.sportbetting.databinding.ItemMyCompsBinding

class SelectSportsAdapter(context: Context) : RecyclerView.Adapter<SelectSportsAdapter.SelectSportsViewHolder>() {

    inner class SelectSportsViewHolder(val binding : ItemMyCompsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectSportsViewHolder {
        val binding = ItemMyCompsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SelectSportsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SelectSportsViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = 10

}