package com.app.sportbetting.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.sportbetting.databinding.FragmentSelectSportsBinding

class SelectSportsFragment : Fragment() {

    private val TAG = "SelectSportsFragment"
    private var binding : FragmentSelectSportsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(binding==null){
            binding = FragmentSelectSportsBinding.inflate(layoutInflater)
            initViews()
        }

        return binding?.root
    }

    private fun initViews(){


    }

}