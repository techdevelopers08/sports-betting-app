package com.app.sportbetting.ui.home

import android.app.Application
import android.os.Bundle
import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.app.sportbetting.R
import com.app.sportbetting.databinding.OddsFragmentBinding
import com.app.sportbetting.models.DailyEventsModel
import com.app.sportbetting.network.Repository
import com.app.sportbetting.ui.adapters.OddsAdapter
import com.app.sportbetting.ui.adapters.RugbyLeagueAdapter
import com.app.sportbetting.utils.Constants
import com.app.sportbetting.utils.MyApplication
import com.app.sportbetting.utils.Status
import com.app.sportbetting.utils.toast
import com.app.sportbetting.viewmodel.SportsViewModel
import com.app.sportbetting.viewmodel.ViewModelFactory


class OddsFragment : Fragment(), OddsAdapter.OnClickOdds, View.OnClickListener {

    lateinit var adapter: OddsAdapter
    lateinit var viewModel: SportsViewModel
    lateinit var rugbyAdapter: RugbyLeagueAdapter

    private var list = ArrayList<DailyEventsModel>()
    private var detailsFragment = DetailsFragment()
    private var tippingFragment = TippingFragment()

    lateinit var binding: OddsFragmentBinding
    var sportsId: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        list = ArrayList()
        binding = OddsFragmentBinding.inflate(layoutInflater)

        initViews()
        setObservers()
        // Inflate the layout for this fragment
        return binding.root
    }

    fun initViews() {

        sportsId = arguments?.getInt("sports_id")
        setupViewModel()
        adapter = OddsAdapter(requireContext(), this)
        rugbyAdapter = RugbyLeagueAdapter(requireContext())
        binding.submitTips.setOnClickListener(this)

        if(sportsId==0){
            viewModel.getDailyMatches()
        }
        else{
            viewModel.getDailyMatchesRl()
        }

//        fetchDailyEvents()
    }

    private fun setObservers() {
        list.clear()


        viewModel.resultDailyMatches.observe(requireActivity()) {

            it.let { data ->
                if (isAdded) {
                    adapter.setData(data.data?.report?.fixture?.match)
                    binding.oddsList.adapter = adapter
                }
            }
        }

        viewModel.resultRugbyLeague.observe(requireActivity()) {

            it.let {
                if (isAdded) {
                    it.data?.fixtures?.teams?.team?.let { it1 ->
                        rugbyAdapter.setData(
                            it.data.fixtures.fixture,
                            it1
                        )
                    }
                  binding.oddsList.adapter= rugbyAdapter

                }
            }
        }


        viewModel.submitTipsResult.observe(requireActivity()) {
            it.let { data ->
                val message = data?.data?.message
                data?.data?.body

                when (data.status) {
                    Status.SUCCESS -> {
                        MyApplication.hideLoader()
                        requireActivity().toast("Tip Submitted Successfully")
                    }
                    Status.LOADING -> {
                        MyApplication.showLoader(requireContext())
                    }
                    Status.ERROR -> {
                        MyApplication.hideLoader()
                        Toast.makeText(
                            requireContext(),
                            it.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }


    private fun fetchDailyEvents() {
        Log.d("TAG", "fetchDailyEvents: $sportsId")
        viewModel.getDailyEvents(
            Constants.USERNAME,
            Constants.TOKEN,
            "Australia/Brisbane",
            sportsId.toString(),
            Constants.languageFk,
            "2021-09-27"
        )
    }


    companion object {

        @JvmStatic
        fun newInstance(): OddsFragment {
            return OddsFragment()
        }

    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<ImageView>(R.id.ivBack).visibility = View.VISIBLE
        requireActivity().findViewById<TextView>(R.id.tvAppBarLabel).text =
            getString(R.string.odds_fragment)
        requireActivity().findViewById<ImageView>(R.id.ivBack).setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun setupViewModel() {
        val viewModelFactory =
            ViewModelFactory(Application(), repository = Repository())
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(SportsViewModel::class.java)
    }

    override fun onClickOdds(
        participant1: String,
        participant2: String,
        venueName: String,
        startDate: String,
    ) {
        val bundle = bundleOf(
            "part_1" to participant1,
            "part_2" to participant2,
            "venue" to venueName,
            "start_date" to startDate,
        )

        detailsFragment.arguments = bundle
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fcView, detailsFragment).addToBackStack(null).commit()
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding.submitTips -> {
                requireActivity().toast("Tip submitted successfully")
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fcView, tippingFragment).addToBackStack(null).commit()
            }
        }
    }


}