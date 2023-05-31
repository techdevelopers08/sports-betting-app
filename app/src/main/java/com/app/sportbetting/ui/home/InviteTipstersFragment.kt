package com.app.sportbetting.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.app.sportbetting.R
import com.app.sportbetting.databinding.InviteTipstersBinding

class InviteTipstersFragment : Fragment() {
    lateinit var binding:InviteTipstersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = InviteTipstersBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<ImageView>(R.id.ivBack).visibility = View.VISIBLE
        requireActivity().findViewById<TextView>(R.id.tvAppBarLabel).text = getString(R.string.invite_tipsters)
        requireActivity().findViewById<ImageView>(R.id.ivBack).setOnClickListener{
            requireActivity().onBackPressed()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance():InviteTipstersFragment{
            return InviteTipstersFragment()
        }

    }
}