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
import com.app.sportbetting.databinding.FragmentJoinCompBinding
import com.app.sportbetting.models.joinComp.JoinCompModel
import com.app.sportbetting.network.Repository
import com.app.sportbetting.utils.MyApplication
import com.app.sportbetting.utils.Status
import com.app.sportbetting.utils.toast
import com.app.sportbetting.viewmodel.CreateCompViewModel
import com.app.sportbetting.viewmodel.ViewModelFactory

class JoinCompFragment : Fragment(),View.OnClickListener {

    private val TAG= "JoinCompFragment"
    lateinit var viewModel:CreateCompViewModel
    private var binding : FragmentJoinCompBinding? = null

    private var fragment = OddsFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(binding==null){
            binding = FragmentJoinCompBinding.inflate(layoutInflater)
            initViews()
            setObservers()
        }

        return binding?.root
    }

    private fun initViews(){
        setupViewModel()
    requireActivity().findViewById<ImageView>(R.id.ivBack).visibility = View.VISIBLE
    requireActivity().findViewById<TextView>(R.id.tvAppBarLabel).text = getString(R.string.join_comp)
        binding?.btnJoinComp?.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<ImageView>(R.id.ivBack).visibility = View.VISIBLE
        requireActivity().findViewById<TextView>(R.id.tvAppBarLabel).text = getString(R.string.join_comp)
    }

    override fun onClick(v: View?) {
        when(v){
            binding?.btnJoinComp->{
                val body = JoinCompModel(binding?.editTextTextPassword?.text.toString(),binding?.etCompNumber?.text.toString())
                viewModel.joinComp(body)
            }
        }
    }

    private fun setObservers() {
        viewModel.joinCompResult.observe(requireActivity()) {
            it.let { data ->
                val message = data?.data?.message
                val body = data?.data?.body

                when (data.status) {
                    Status.SUCCESS -> {
                        MyApplication.hideLoader()

                        requireActivity().toast(message!!)

                        val bundle = bundleOf("sports_id" to body?.sports_id)

                        Log.d(TAG, "setObservers: ${body?.sports_id}")

                        fragment.arguments = bundle

                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.fcView, fragment).addToBackStack(null).commit()
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

    private fun setupViewModel() {
        val viewModelFactory =
            ViewModelFactory(Application(), repository = Repository())
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(CreateCompViewModel::class.java)
    }


}