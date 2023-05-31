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
import com.app.sportbetting.databinding.FragmentCompBinding
import com.app.sportbetting.models.getComp.Body
import com.app.sportbetting.network.Repository
import com.app.sportbetting.ui.adapters.MyCompAdapter
import com.app.sportbetting.utils.MyApplication
import com.app.sportbetting.utils.Status
import com.app.sportbetting.viewmodel.GetCompViewModel
import com.app.sportbetting.viewmodel.ViewModelFactory

class MyCompFragment : Fragment(),MyCompAdapter.OnClickComp {

    private val TAG = "CompFragment"
    private var binding : FragmentCompBinding? = null

    val fragment = OddsFragment()
    lateinit var adapter: MyCompAdapter
    lateinit var viewModel:GetCompViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(binding==null){
            binding = FragmentCompBinding.inflate(layoutInflater)
            initViews()
            setObservers()
        }
        return binding?.root
    }



    private fun initViews(){
        adapter = MyCompAdapter(requireContext(),this)
        setupViewModel()
        binding?.rvComp?.scheduleLayoutAnimation()

    }


    private fun setObservers() {
        viewModel.getCompResult.observe(requireActivity()) {
            it.let { data ->
                val message = data?.data?.message
                val body = data?.data?.body

                when (data.status) {
                    Status.SUCCESS -> {
                        if (isAdded) {
                            MyApplication.hideLoader()
                            if (body != null) {
                                setList(body)
                                Log.d(TAG, "setObservers: $body")
                            }
                        }

                    }
                    Status.LOADING -> {
                        if (isAdded) {
                            MyApplication.showLoader(requireActivity())
                        }

                    }
                    Status.ERROR -> {
                        MyApplication.hideLoader()
                        Toast.makeText(
                            requireActivity(),
                            it.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }


    }

    private fun setList(body: List<Body>) {
        adapter.setData(body)
        binding?.rvComp?.adapter = adapter
    }


    private fun setupViewModel() {
        val viewModelFactory =
            ViewModelFactory(Application(), repository = Repository())
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(GetCompViewModel::class.java)
    }


    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<ImageView>(R.id.ivBack).visibility = View.GONE
        requireActivity().findViewById<TextView>(R.id.tvAppBarLabel).text = getString(R.string.my_comp)
            fetchList()

    }

    private fun fetchList() {

        viewModel.getCompList()
    }

    override fun onClickComp(sportsId: Int) {
        val bundle = bundleOf("sports_id" to sportsId)
        Log.d(TAG, "onClickComp: $sportsId")
        fragment.arguments=bundle
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fcView,fragment).addToBackStack(null).commit()
    }

}