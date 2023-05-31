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
import com.app.sportbetting.databinding.FragmentCreateComp3Binding
import com.app.sportbetting.models.createComp.CreateCompModel
import com.app.sportbetting.network.Repository
import com.app.sportbetting.utils.MyApplication
import com.app.sportbetting.utils.Status
import com.app.sportbetting.utils.toast
import com.app.sportbetting.viewmodel.CreateCompViewModel
import com.app.sportbetting.viewmodel.ViewModelFactory

class CreateCompFragment3 : Fragment() {

    var binding: FragmentCreateComp3Binding? = null
    var knockComp: Int? = null



    var jokerRound: Int? = null

    lateinit var viewModel: CreateCompViewModel

    var margin: Int? = null

    var prizeManagement: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if (binding == null) {
            binding = FragmentCreateComp3Binding.inflate(layoutInflater)
        }
        initView()
        setObservers()
        return binding?.root
    }

    private fun initView() {

        setupViewModel()
    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<ImageView>(R.id.ivBack).visibility = View.VISIBLE
        requireActivity().findViewById<TextView>(R.id.tvAppBarLabel).text = getString(R.string.create_comp)
        requireActivity().findViewById<ImageView>(R.id.ivBack).setOnClickListener{
            requireActivity().onBackPressed()
        }
    }

    private fun setObservers() {
        viewModel.createCompResult.observe(requireActivity(), {
            it.let { data ->
                val message = data?.data?.message
                val body = data?.data?.body

                when (data.status) {
                    Status.SUCCESS -> {
                        MyApplication.hideLoader()
                        requireActivity().toast(message!!)
                        Log.d("TAG", "setObservers: $body")
                        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fcView,MyCompFragment()).addToBackStack(null).setReorderingAllowed(true).commit()


//                        findNavController().navigate(R.id.action_createCompFragment3_to_compFragment)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding?.createComp?.setOnClickListener {
            if (checkValid()) {
                val compName = arguments?.getString("comp_name")
                val password = arguments?.getString("password")
                val points = arguments?.getInt("points")
                val price = arguments?.getInt("price")
                val firstGame = arguments?.getInt("first_game")
                val everyGame = arguments?.getInt("every_game")
                val sportsId = arguments?.getInt("sportsId")

                val tipWinner = arguments?.getString("tip_winner")
                val tipDraw = arguments?.getString("tip_draw")

                val perfectRounds = arguments?.getString("perfect_rounds")
                var bonusForRounds = arguments?.getInt("bonus_for_rounds")

                val finals = arguments?.getInt("finals")

                val hideLadder = arguments?.getString("hide_ladder")
                val bettingOdds = arguments?.getInt("betting")

                val hideTipsters = arguments?.getInt("hide_tipsters")

                val nonSub = arguments?.getString("non_sub")
                val joinLate = arguments?.getString("join_late")

                val sportsName = arguments?.getString("sports_name")
                val prizeInfo = arguments?.getString("prize_info")
                Log.d("TAG", "onViewCreated: $sportsId")

                val body = price?.let { it1 ->
                    CreateCompModel(
                        sportsName!!,
                        password.toString(),
                        compName!!,
                        points!!,
                        it1,
                        firstGame!!,
                        everyGame!!,
                        tipWinner!!.toInt(),
                        tipDraw!!.toInt(),
                        perfectRounds!!.toInt(),
                        bonusForRounds!!,
                        nonSub!!,
                        joinLate!!,
                        finals!!,
                        hideLadder.toString(),
                        hideTipsters!!,
                        bettingOdds!!,
                        prizeInfo!!,
                        knockComp.toString(),
                        jokerRound.toString(),
                        margin.toString(),
                        prizeManagement.toString(),
                        sportsId!!
                    )
                }

                if (body != null) {
                    viewModel.createComp(body)
                }
            }

        }

    }

    private fun checkValid(): Boolean {

        knockComp = if (binding?.includeKnockRadio?.checkedRadioButtonId != -1) {
            if (binding?.knockYes?.isSelected == true) {
                1
            } else {
                0
            }
        } else {
            requireActivity().toast("Please select knock comp")
            return false
        }


        margin = if (binding?.marginRadio?.checkedRadioButtonId != -1) {
            if (binding?.marginYes?.isSelected == true) {
                1
            } else {
                0
            }
        } else {
            requireActivity().toast("Please select knock comp")
            return false
        }


        prizeManagement = if (binding?.prizeRadio?.checkedRadioButtonId != -1) {
            if (binding?.prizeYes?.isSelected == true) {
                1
            } else {
                0
            }
        } else {
            requireActivity().toast("Please select knock comp")
            return false
        }

        jokerRound = if (binding?.jokerRadio?.checkedRadioButtonId != -1) {
            if (binding?.jokerYes?.isSelected == true) {
                1
            } else {
                0
            }
        } else {
            requireActivity().toast("Please select knock comp")
            return false
        }

        return true

    }


    private fun setupViewModel() {
        val viewModelFactory =
            ViewModelFactory(Application(), repository = Repository())
        viewModel =
            ViewModelProvider(
                this,
                viewModelFactory
            ).get(CreateCompViewModel::class.java)
    }


    companion object {

        @JvmStatic
        fun newInstance(): CreateCompFragment3 {
            return CreateCompFragment3()
        }

    }
}