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
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.app.sportbetting.R
import com.app.sportbetting.databinding.FragmentSelectSportsOptionsBinding
import com.app.sportbetting.models.SportsModel
import com.app.sportbetting.network.Repository
import com.app.sportbetting.ui.adapters.SelectSportsOptionsAdapter
import com.app.sportbetting.viewmodel.SportsViewModel
import com.app.sportbetting.viewmodel.ViewModelFactory

class SelectSportsOptionsFragment : Fragment(), SelectSportsOptionsAdapter.OnSelectSports,
    View.OnClickListener {

    private val TAG = "SelectSportsOptionsFragment"
    val fragment = CreateCompForm1Fragment()
    lateinit var viewModel: SportsViewModel
    private lateinit var binding: FragmentSelectSportsOptionsBinding
    var sportsName: String? = null
    var adapter: SelectSportsOptionsAdapter? = null
    private var list: ArrayList<SportsModel>? = null

    var sportsList  = ArrayList<SportsModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectSportsOptionsBinding.inflate(layoutInflater)

        inView()
        setObservers()
        list = ArrayList()
        adapter = SelectSportsOptionsAdapter(requireContext(), this)
        return binding.root
    }

    private fun inView() {
        setupViewModel()
        binding.sportsBack.setOnClickListener(this)
    }

    private fun setObservers() {
        viewModel.data.observe(requireActivity()) {
            it.let { data ->
                adapter?.setData(data)
                binding.rvSportsOptions.adapter = adapter
            }
        }
    }


    private fun setupViewModel() {
        val viewModelFactory =
            ViewModelFactory(Application(), repository = Repository())
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(SportsViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()

        requireActivity().findViewById<ImageView>(R.id.ivBack).visibility = View.VISIBLE
        requireActivity().findViewById<TextView>(R.id.tvAppBarLabel).text =
            getString(R.string.create_comp)
        requireActivity().findViewById<ImageView>(R.id.ivBack).setOnClickListener {
            requireActivity().onBackPressed()
        }
        fetchList()
        Log.d("TAG", "onResume: $list")

    }

    private fun fetchList() {

        sportsList.add(SportsModel("AFL","",0))
        sportsList.add(SportsModel("NRL","",1))
        adapter?.setData(sportsList)
        binding.rvSportsOptions.adapter = adapter

       /* viewModel.getSportsList(Constants.USERNAME, Constants.TOKEN, "Australia/Brisbane")
        adapter?.setData(list)
        binding.rvSportsOptions.adapter = adapter*/

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

    override fun onSelect(sportsName: String, sportsId: Int?) {
        this.sportsName = sportsName
        Log.d("TAG", "onSelect: $sportsId")
        binding.btnSportsOption.setOnClickListener {
            val bundle = bundleOf("sports_name" to sportsName,"sports_id" to sportsId)
            fragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fcView, fragment).addToBackStack(null).commit()
        }
    }

    override fun onClick(p0: View?) {
        when(p0){
            binding.sportsBack->{
                requireActivity().onBackPressed()
            }
        }
    }


}