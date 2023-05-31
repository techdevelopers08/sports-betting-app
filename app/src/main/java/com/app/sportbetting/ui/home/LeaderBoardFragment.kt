package com.app.sportbetting.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.app.sportbetting.R
import com.app.sportbetting.databinding.FragmentLeaderBoardBinding
import com.app.sportbetting.models.leaderboard.LeaderboardModel
import com.app.sportbetting.ui.adapters.LeaderboardAdapter


class LeaderBoardFragment : Fragment() {
    var list = emptyList<LeaderboardModel>()
    lateinit var binding:FragmentLeaderBoardBinding
    lateinit var adapter:LeaderboardAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLeaderBoardBinding.inflate(layoutInflater)
        adapter = LeaderboardAdapter(requireContext())
        initView()
        return binding.root
    }

    private fun initView() {

        list = ArrayList()
        (list as ArrayList<LeaderboardModel>).add(LeaderboardModel("Pankaj",12.45,5))
        (list as ArrayList<LeaderboardModel>).add(LeaderboardModel("Ankit",12.45,5))

        (list as ArrayList<LeaderboardModel>).add(LeaderboardModel("Riya",12.45,5))

        (list as ArrayList<LeaderboardModel>).add(LeaderboardModel("Arun",12.45,5))

        (list as ArrayList<LeaderboardModel>).add(LeaderboardModel("Sanjana",12.45,5))

        (list as ArrayList<LeaderboardModel>).add(LeaderboardModel("Rahul",12.45,5))

        adapter.setData(list as ArrayList<LeaderboardModel>)
        binding.leaderList.adapter = adapter

    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<ImageView>(R.id.ivBack).visibility = View.GONE
        requireActivity().findViewById<TextView>(R.id.tvAppBarLabel).text = getString(R.string.leader_board)
    }


    companion object {

        @JvmStatic
        fun newInstance():LeaderBoardFragment{
            return LeaderBoardFragment()
        }
    }
}