package com.app.sportbetting.ui.adapters

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.app.sportbetting.databinding.MatchItemsBinding
import com.app.sportbetting.models.rugbyLeague.Fixture
import com.app.sportbetting.models.rugbyLeague.TeamX
import com.app.sportbetting.utils.Utility


class RugbyLeagueAdapter(val context: Context) :
    RecyclerView.Adapter<RugbyLeagueAdapter.RugbyViewHolder>() {

    var list = emptyList<Fixture>()
    var teamsList = emptyList<TeamX>()


    inner class RugbyViewHolder(val binding: MatchItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.O)
        fun bindData(position: Int) {
            binding.location.text = list[position].attributes.venue

            val team1 = getTeamId(list[position].team[0].attributes.team_id)

            val team2 = getTeamId(list[position].team[1].attributes.team_id)

            binding.team1Image.text = team1
            binding.team2Image.text = team2


            try {
                binding.date.text = Utility.getDateTime(list[position].attributes.datetime)
                binding.time.text = Utility.getRlTime(list[position].attributes.datetime)
            } catch (e: Exception) {
                Log.d("TAG", "bindData:$e ")
            }

        }

        private fun getTeamId(teamId: String): String {
            var teamName = ""
            teamsList.forEach {
                if (it.attributes.id == teamId) {
                    teamName = it.attributes.name
                }
            }
            return teamName
        }


    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RugbyLeagueAdapter.RugbyViewHolder {
        return RugbyViewHolder(
            MatchItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun getItemCount(): Int = list.size

    fun setData(fixture: List<Fixture>?, teams: List<TeamX>) {
        if (fixture != null) {
            list = fixture
        }

        teamsList = teams

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RugbyLeagueAdapter.RugbyViewHolder, position: Int) {
        holder.bindData(position)
    }


}