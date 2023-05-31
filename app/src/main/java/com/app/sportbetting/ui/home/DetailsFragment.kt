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
import androidx.lifecycle.ViewModelProvider
import com.app.sportbetting.R
import com.app.sportbetting.databinding.DetailsFragmentBinding
import com.app.sportbetting.network.Repository
import com.app.sportbetting.utils.MyApplication
import com.app.sportbetting.utils.Status
import com.app.sportbetting.utils.toast
import com.app.sportbetting.viewmodel.SportsViewModel
import com.app.sportbetting.viewmodel.ViewModelFactory


class DetailsFragment : Fragment(), View.OnClickListener {
    lateinit var binding:DetailsFragmentBinding
    lateinit var viewModel:SportsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DetailsFragmentBinding.inflate(layoutInflater)
        initViews()
        setObservers()
        return binding.root
    }
    private fun setObservers() {
        viewModel.submitTipsResult.observe(requireActivity(), {
            it.let { data ->
                val message = data?.data?.message
                val body = data?.data?.body

                when (data.status) {
                    Status.SUCCESS -> {
                        MyApplication.hideLoader()
                        Log.d("TAG", "setObservers: $body")
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
        })
    }

    fun initViews() {
        setupViewModel()
        binding.submitTipsDetail.setOnClickListener(this)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.date.text = arguments?.getString("start_date")
        binding.time.text = arguments?.getString("time")
        binding.team1Image.text = arguments?.getString("part_1")

        binding.team2Image.text = arguments?.getString("part_2")
        binding.location.text = arguments?.getString("venue")
    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<ImageView>(R.id.ivBack).visibility = View.VISIBLE
        requireActivity().findViewById<TextView>(R.id.tvAppBarLabel).text = getString(R.string.detail)
        requireActivity().findViewById<ImageView>(R.id.ivBack).setOnClickListener{
            requireActivity().onBackPressed()
        }
    }

    private fun setupViewModel() {
        val viewModelFactory =
            ViewModelFactory(Application(), repository = Repository())
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(SportsViewModel::class.java)
    }

    companion object {

        @JvmStatic
        fun newInstance():DetailsFragment{
            return DetailsFragment()
        }
    }

    override fun onClick(p0: View?) {
        when(p0){
            binding.submitTipsDetail->{
                if(!binding.marginVal.text.isNullOrEmpty()){
                    if(binding.matchRadioGroup.checkedRadioButtonId!=-1){
                        requireActivity().toast("Tip Submitted successfully")
                    }
                    else{
                        requireActivity().toast("Please select a team")
                    }
                }
                else{
                    requireActivity().toast("Please enter margin")
                }
            }
        }
    }

}