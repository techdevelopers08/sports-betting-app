package com.app.sportbetting.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.app.sportbetting.R
import com.app.sportbetting.databinding.FragmentHomeBinding


class HomeFragment : Fragment(),View.OnClickListener {

    private val TAG = "HomeFragment"
    private var binding : FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(binding==null){
            binding = FragmentHomeBinding.inflate(layoutInflater)
            initViews()
        }
        return binding?.root
    }




    private fun initViews(){

        binding?.btnTipping?.setOnClickListener(this)
        binding?.btnMyProfile?.setOnClickListener(this)
    }
    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<ImageView>(R.id.ivBack).visibility = View.GONE
        requireActivity().findViewById<TextView>(R.id.tvAppBarLabel).text = getString(R.string.home)
    }

    override fun onClick(v: View?) {
        when(v){
            binding?.btnTipping->{
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fcView,TippingFragment()).addToBackStack(null).setReorderingAllowed(true).commit()
            }
            binding?.btnMyProfile->{
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fcView,MyAccount()).addToBackStack(null).setReorderingAllowed(true).commit()
            }
        }
    }



}