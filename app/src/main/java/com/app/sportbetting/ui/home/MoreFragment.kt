package com.app.sportbetting.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.app.sportbetting.R
import com.app.sportbetting.databinding.MoreBinding


class MoreFragment : Fragment(), View.OnClickListener {

    lateinit var binding:MoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = MoreBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<ImageView>(R.id.ivBack).visibility = View.GONE
        requireActivity().findViewById<TextView>(R.id.tvAppBarLabel).text = getString(R.string.more)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.constraintViewAll.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fcView,ViewAllTipsFragment()).addToBackStack(null).setReorderingAllowed(true).commit()
        }
        binding.constraintManager.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fcView,ManagerLogFragment()).addToBackStack(null).setReorderingAllowed(true).commit()
        }
        binding.inviteTipsParent.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fcView,InviteTipstersFragment()).addToBackStack(null).setReorderingAllowed(true).commit()
        }

    }

    companion object {

        @JvmStatic
        fun newInstance():MoreFragment{
            return MoreFragment()
        }

    }

    override fun onClick(v: View?) {
        when(v){
            binding.inviteTipsParent->{
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fcView,InviteTipstersFragment())
            }
            binding.constraintManager->{
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fcView,ManagerLogFragment())

            }
            binding.constraintViewAll->{


            }
            /*binding.myTippingLogConstraint->{
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fcView)

            }*/
        }

    }
}