package com.app.sportbetting.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.app.sportbetting.databinding.MatchItemsBinding
import com.app.sportbetting.models.oddModel.MatchItem
import com.app.sportbetting.utils.Utility

class OddsAdapter(context: Context,var onClick:OnClickOdds) :
    RecyclerView.Adapter<OddsAdapter.MatchViewHolder>() {
    lateinit var itemBinding:MatchItemsBinding
    var list = emptyList<MatchItem>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MatchViewHolder {
        itemBinding = MatchItemsBinding
            .inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return MatchViewHolder(itemBinding.root)
    }


    class MatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        itemBinding.team1Image.text = list[position].attributes?.homeSquadName
        itemBinding.team2Image.text = list[position].attributes?.awaySquadName
        itemBinding.location.text = list[position].attributes?.venueName
        itemBinding.date.text = list[position].attributes?.utcStartTime?.let { Utility.getDate(it) }


        holder.itemView.setOnClickListener{
            list[position].attributes?.awaySquadName?.let { it1 ->
                list[position].attributes?.homeSquadName?.let { it2 ->
                    list[position].attributes?.venueName?.let { it3 ->
                        list[position].attributes?.localStartTime?.let { it4 ->
                            onClick.onClickOdds(
                                it2,
                                it1,
                                it3,
                                it4,
                                )
                        }
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<MatchItem?>?) {
        if (data != null) {
            this.list = data as List<MatchItem>
           notifyDataSetChanged()
        }
    }

    interface OnClickOdds {
        fun onClickOdds(
            participant1: String,
            participant2: String,
            venueName: String,
            startDate: String,
        )
    }


}
