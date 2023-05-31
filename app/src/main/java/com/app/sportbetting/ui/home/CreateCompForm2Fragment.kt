package com.app.sportbetting.ui.home

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.app.sportbetting.R
import com.app.sportbetting.databinding.FragmentCreateCompForm2Binding
import com.app.sportbetting.models.getModels.tipDraw.Body
import com.app.sportbetting.network.Repository
import com.app.sportbetting.utils.MyApplication
import com.app.sportbetting.utils.Status
import com.app.sportbetting.utils.toast
import com.app.sportbetting.viewmodel.GetCompViewModel
import com.app.sportbetting.viewmodel.ViewModelFactory


class CreateCompForm2Fragment : Fragment(), View.OnClickListener {

    var binding: FragmentCreateCompForm2Binding? = null
    lateinit var tipDrawList: ArrayList<String>
    lateinit var submissionList: ArrayList<String>
    lateinit var hideLadderList: ArrayList<String>
    val fragment = CreateCompFragment3()
    var hide: Int? = null
    var bettingOdds: Int? = null
    var finals: Int? = null
    var bonusForRounds: Int? = null
    lateinit var viewModel: GetCompViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if (binding == null) {
            binding = FragmentCreateCompForm2Binding.inflate(layoutInflater)
        }

        tipDrawList = ArrayList()
        submissionList = ArrayList()
        hideLadderList = ArrayList()
        initView()
        setObservers()

        return binding?.root
    }

    private fun initView() {
        setSpinner()
        setupViewModel()
        fetchList()
    }

    private fun setSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.hide_tip,
            R.layout.text_view
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding?.hideTipstersSpinner?.adapter = adapter
        }


        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.tip_a_winner_value,
            R.layout.text_view
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding?.tipAWinnerSpinner?.adapter = adapter
        }


        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.betting_odds_value,
            R.layout.text_view
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding?.bettingOddsSpinner?.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.tip_a_winner_value,
            R.layout.text_view
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding?.perfectRoundSpinner?.adapter = adapter
        }


    }


    private fun fetchList() {
        viewModel.submissionList()
        viewModel.ladderList()
        viewModel.tipDrawlist()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.btnForm1?.setOnClickListener {
            if (isFieldEmpty()) {
                if (checkValidations()) {
                    val tipAWinner = binding?.tipAWinnerSpinner?.selectedItem.toString()
                    val tipADraw = binding?.tipDrawSpinner?.selectedItem.toString()
                    val perfectRounds = binding?.perfectRoundSpinner?.selectedItem.toString()
                    val compName = arguments?.getString("comp_name")
                    val password = arguments?.getString("password")
                    val points = arguments?.getInt("points")
                    val price = arguments?.getInt("price")
                    val firstGame = arguments?.getInt("first_game")

                    val sportsId = arguments?.getInt("sports_id")
                    Log.d("TAG", "onViewCreated: $sportsId")
                    val everyGame = arguments?.getInt("every_game")
                    val sportsName = arguments?.getString("sports_name")
                    val prizeInfo = binding?.prizeInfoVal?.text.toString()

                    val nonSub = binding?.nonSubSpinner?.selectedItem.toString()
                    val joinLate = binding?.joinLateSpinner?.selectedItem.toString()

                    val hideLadder = binding?.hideLaddersSpinners?.selectedItem.toString()
                    val hideTipsters = binding?.hideTipstersSpinner?.selectedItem.toString()

                    val bettingOdds = binding?.bettingOddsSpinner?.selectedItem.toString()
                    var betting: Int? = null
                    if (bettingOdds == "Yes") {
                        betting = 1
                    } else {
                        betting = 0
                    }
                    if (hideTipsters == "Hide") {
                        hide = 1
                    } else {
                        hide = 0
                    }

                    val bundle =
                        bundleOf(
                            "comp_name" to compName,
                            "password" to password,
                            "points" to points,
                            "price" to price,
                            "first_game" to firstGame,
                            "every_game" to everyGame,
                            "tip_winner" to tipAWinner,
                            "tip_draw" to tipADraw,
                            "perfect_rounds" to perfectRounds,
                            "bonus_for_rounds" to bonusForRounds,
                            "finals" to finals,
                            "hide_ladder" to hideLadder,
                            "betting_odds" to betting,
                            "hide_tipsters" to hide,
                            "non_sub" to nonSub,
                            "join_late" to joinLate,
                            "sports_name" to sportsName,
                            "prize_info" to prizeInfo,
                            "sportsId" to sportsId
                        )

                    fragment.arguments = bundle
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fcView, fragment).addToBackStack(null).setReorderingAllowed(true).commit()

                    /* findNavController().navigate(
                         R.id.action_createCompForm2Fragment_to_createCompFragment3,
                         bundle
                     )*/
                }
            }
        }
        binding?.back1?.setOnClickListener(this)

    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<ImageView>(R.id.ivBack).visibility = View.VISIBLE
        requireActivity().findViewById<TextView>(R.id.tvAppBarLabel).text =
            getString(R.string.create_comp)
        requireActivity().findViewById<ImageView>(R.id.ivBack).setOnClickListener {
            requireActivity().onBackPressed()
        }

    }

    private fun checkValidations(): Boolean {


        finals = if (binding?.finalRadio?.checkedRadioButtonId != -1) {
            if (binding?.yesFinals?.isSelected == true) {
                1
            } else {
                0
            }

        } else {
            requireActivity().toast("Please Select finals options")
            return false
        }

        bonusForRounds = if (binding?.includePerfectRoundRadio?.checkedRadioButtonId != -1) {
            if (binding?.perfectYes?.isSelected == true) {
                1
            } else {
                0
            }

        } else {
            requireActivity().toast("Please Select finals options")
            return false
        }
        return true
    }

    private fun isFieldEmpty(): Boolean {
        var isEmpty = true

        when {
            binding?.prizeInfoVal?.text?.toString().isNullOrBlank() -> {
                isEmpty = false
                requireActivity().toast("Please Enter prize info")
            }
            binding?.finalRadio?.checkedRadioButtonId == -1 -> {
                isEmpty = false
                requireActivity().toast("Please select finals  yes or no")

            }
            binding?.includePerfectRoundRadio?.checkedRadioButtonId == -1 -> {
                isEmpty = false
                requireActivity().toast("Please select include perfect bonus or not")
            }
        }
        return isEmpty
    }


    private fun setupViewModel() {
        val viewModelFactory =
            ViewModelFactory(Application(), repository = Repository())
        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory).get(GetCompViewModel::class.java)
    }

    private fun setObservers() {
        viewModel.submissionListResult.observe(requireActivity()) {
            it.let { data ->
                val message = data?.data?.message
                val body = data?.data?.body

                when (data.status) {
                    Status.SUCCESS -> {
                        MyApplication.hideLoader()
                        if (body != null) {
                            setNonSubSpinnerList(body)
                        }
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



        viewModel.tipDrawListResult.observe(requireActivity()) {
            it.let { data ->
                val message = data?.data?.message
                val body = data?.data?.body

                when (data.status) {
                    Status.SUCCESS -> {
                        MyApplication.hideLoader()
                        if (body != null) {
                            setTipDrawSpinner(body)
                        }
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

        viewModel.hideLadderListResult.observe(requireActivity()) {
            it.let { data ->
                val message = data?.data?.message
                val body = data?.data?.body

                when (data.status) {
                    Status.SUCCESS -> {
                        MyApplication.hideLoader()
                        if (body != null) {
                            setHideLadderList(body)
                        }
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

    private fun setTipDrawSpinner(body: List<Body>) {
        tipDrawList.clear()
        body.forEach {
            tipDrawList.add(
                it.tip_draw_name
            )
        }

        binding?.tipDrawSpinner?.adapter = ArrayAdapter(
            requireContext(),
            R.layout.text_view,
            tipDrawList.toArray()
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
        }


    }

    private fun setNonSubSpinnerList(body: List<com.app.sportbetting.models.getModels.submissionList.Body>) {
        submissionList.clear()
        body.forEach {
            submissionList.add(
                it.submission_name
            )
        }
        binding?.nonSubSpinner?.adapter = ArrayAdapter(
            requireContext(),
            R.layout.text_view,
            submissionList.toArray()
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
        }


        binding?.joinLateSpinner?.adapter = ArrayAdapter(
            requireContext(),
            R.layout.text_view,
            submissionList.toArray()
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
        }

    }

    private fun setHideLadderList(body: List<com.app.sportbetting.models.getModels.hideLadder.Body>) {

        hideLadderList.clear()
        body.forEach {
            hideLadderList.add(
                it.ladder_round
            )
        }

        binding?.hideLaddersSpinners?.adapter = ArrayAdapter(
            requireContext(),
            R.layout.text_view,
            hideLadderList.toArray()
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
        }


    }

    override fun onClick(p0: View?) {
        when(p0){
            binding?.back1->{
                requireActivity().onBackPressed()
            }
        }
    }


}