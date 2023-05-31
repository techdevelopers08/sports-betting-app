package com.app.sportbetting.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.sportbetting.R
import com.app.sportbetting.databinding.ItemSportsOptionsAdapterBinding
import com.app.sportbetting.models.SportsModel
import java.util.ArrayList

class SelectSportsOptionsAdapter(context: Context, var onSelect:OnSelectSports) : RecyclerView.Adapter<SelectSportsOptionsAdapter.SelectSportsOptionsViewHolder>() {

    private var list = emptyList<SportsModel>()
    inner class SelectSportsOptionsViewHolder(val binding : ItemSportsOptionsAdapterBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectSportsOptionsViewHolder {
        val binding = ItemSportsOptionsAdapterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SelectSportsOptionsViewHolder(binding)
    }

    var rowIndex:Int?=null

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: SelectSportsOptionsViewHolder, position: Int) {


        if (rowIndex == position) {
            holder.binding.optionParent.setBackgroundResource(R.drawable.layout_left_corners_with_solid)
        } else {

            holder.binding.optionParent.setBackgroundResource(R.drawable.layout_corners_with_solid)
        }


        holder.binding.textView25.text = list[position].sportsName
        if(list[position].image==null){
            holder.binding.imageView5.setImageResource(R.drawable.ic_football)
        }

        holder.itemView.setOnClickListener{
            rowIndex=position
            notifyDataSetChanged()
            list[position].sportsId?.let { it1 ->
                onSelect.onSelect(list[position].sportsName,
                    it1
                )
            }
        }

    }

    fun setData(list: ArrayList<SportsModel>?) {
        if (list != null) {
            this.list = list
        }
    }


    interface OnSelectSports {
        fun onSelect(sportsName: String, sportsId: Int?)
    }

    override fun getItemCount(): Int {
        return list.size
    }


}