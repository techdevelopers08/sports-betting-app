package com.app.sportbetting.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.app.sportbetting.R
import com.app.sportbetting.databinding.FragmentTippingBinding

class TippingFragment : Fragment(),View.OnClickListener {

    private val TAG = "TippingFragment"
    private var binding : FragmentTippingBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(binding==null){
            binding = FragmentTippingBinding.inflate(layoutInflater)
            initViews()
        }

        return binding?.root
    }

    private fun initViews(){


        binding?.btnCreateComp?.setOnClickListener(this)
        binding?.btnJoinComp?.setOnClickListener(this)
    }


    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<ImageView>(R.id.ivBack).visibility = View.VISIBLE
        requireActivity().findViewById<TextView>(R.id.tvAppBarLabel).text = getString(R.string.tipping)
        requireActivity().findViewById<ImageView>(R.id.ivBack).setOnClickListener{
            requireActivity().onBackPressed()
        }
    }

    override fun onClick(v: View?) {
        when(v){
            binding?.btnCreateComp->{
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fcView,SelectSportsOptionsFragment()).addToBackStack(null).setReorderingAllowed(true).commit()
            }
            binding?.btnJoinComp->{
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fcView,JoinCompFragment()).addToBackStack(null).setReorderingAllowed(true).commit()
            }
        }
    }

}