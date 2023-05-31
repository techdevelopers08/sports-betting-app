package com.app.sportbetting.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import com.app.sportbetting.R
import com.app.sportbetting.databinding.FragmentCreateCompForm1Binding
import com.app.sportbetting.utils.toast

class CreateCompForm1Fragment : Fragment(), View.OnClickListener {

    var binding: FragmentCreateCompForm1Binding? = null

    val fragment=CreateCompForm2Fragment()
    var point: Int? = null
    var price: Int? = null
    var firstGame: Int? = null
    var everyGame: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if (binding == null) {

            binding = FragmentCreateCompForm1Binding.inflate(layoutInflater)

        }

        return binding?.root
    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<ImageView>(R.id.ivBack).visibility = View.VISIBLE
        requireActivity().findViewById<TextView>(R.id.tvAppBarLabel).text = getString(R.string.create_comp)
        requireActivity().findViewById<ImageView>(R.id.ivBack).setOnClickListener{
            requireActivity().onBackPressed()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnSignIn?.setOnClickListener {
            if (isFieldEmpty()) {
                if (checkValidations()) {
                    val compName = binding?.nameYourComp?.text.toString()
                    val password = binding?.editTextTextPassword?.text.toString()
                    val sportsName = arguments?.getString("sports_name")
                    val sportsId = arguments?.getInt("sports_id")

                    Log.d("TAG", "onViewCreated: $sportsId")
                    val bundle =
                        bundleOf(
                            "comp_name" to compName,
                            "password" to password,
                            "points" to point,
                            "price" to price,
                            "first_game" to firstGame,
                            "every_game" to everyGame,
                            "sports_name" to sportsName,
                            "sports_id" to sportsId
                        )
                    fragment.arguments = bundle
                    requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fcView,fragment).addToBackStack(null).setReorderingAllowed(true).commit()
                }
            }
        }
        binding?.back2?.setOnClickListener(this)
    }

    private fun checkValidations(): Boolean {
        if (binding?.scoringTips?.checkedRadioButtonId != -1) {
            if (binding?.points?.isSelected == true) {
                point = 1
                price = 0
            } else {
                point = 0
                price = 1
            }
        } else {
            requireActivity().toast("Please select either point or price")
            return false

        }
        if (binding?.scoringTips2?.checkedRadioButtonId != -1) {
            if (binding?.firstGame?.isSelected == true) {
                firstGame = 1
                everyGame = 0
            } else {
                firstGame = 0
                everyGame = 1
            }
        } else {
            requireActivity().toast("Please select either point or price")
            return false
        }
        return true
    }

    private fun isFieldEmpty(): Boolean {
        var isEmpty = true
        when {
            binding?.nameYourComp?.text?.toString().isNullOrBlank() -> {
                isEmpty = false
                requireActivity().toast("Please Name your comp")
            }
            binding?.editTextTextPassword?.text?.toString().isNullOrBlank() -> {
                isEmpty = false
                requireActivity().toast("Please Enter Password")
            }
            binding?.etConfirmPassword?.text?.toString().isNullOrBlank() -> {
                isEmpty = false
                requireActivity().toast("Please Verify your Password")
            }
        }
        return isEmpty
    }

    override fun onClick(p0: View?) {
        when(p0){
            binding?.back2->{
                requireActivity().onBackPressed()
            }
        }
    }

}