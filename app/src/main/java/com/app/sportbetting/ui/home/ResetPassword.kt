package com.app.sportbetting.ui.home

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.app.sportbetting.R
import com.app.sportbetting.databinding.FragmentResetPasswordBinding
import com.app.sportbetting.models.changePassword.ChangePasswordModel
import com.app.sportbetting.network.Repository
import com.app.sportbetting.utils.MyApplication
import com.app.sportbetting.utils.SharedPreference
import com.app.sportbetting.utils.Status
import com.app.sportbetting.utils.Utility
import com.app.sportbetting.viewmodel.AuthViewModel
import com.app.sportbetting.viewmodel.ViewModelFactory


class ResetPassword : Fragment(), View.OnClickListener {
    lateinit var binding: FragmentResetPasswordBinding
    lateinit var viewModel: AuthViewModel
    private var preference: SharedPreference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResetPasswordBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        initView()
        setObservers()
        return binding.root
    }


    companion object {
        @JvmStatic
        fun newInstance(): ResetPassword {
            return ResetPassword()
        }
    }


    private fun initView() {
        setupViewModel()
        preference = SharedPreference.getInstance(requireContext())
        binding.resetPasswordConfirm.setOnClickListener(this)

    }


    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<ImageView>(R.id.ivBack).visibility = View.GONE
        requireActivity().findViewById<TextView>(R.id.tvAppBarLabel).text =
            getString(R.string.reset_password)
        requireActivity().findViewById<ImageView>(R.id.ivBack).visibility=View.VISIBLE
        requireActivity().findViewById<ImageView>(R.id.ivBack).setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.resetPasswordConfirm -> {
                if (Utility.isInternetConnected(requireContext())) {
                    if (isFieldEmpty()) {
                        changePassword()
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Unable to connect to Internet",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


    private fun isFieldEmpty(): Boolean {
        var isEmpty = true
        when {
            binding.newPassword.text?.toString().isNullOrEmpty() -> {
                isEmpty = false
                Toast.makeText(requireContext(), "Enter new Password", Toast.LENGTH_SHORT)
                    .show()
            }
            binding.confirmPassword.text?.toString().isNullOrBlank() -> {
                isEmpty = false
                Toast.makeText(requireContext(), "Confirm your Password", Toast.LENGTH_SHORT).show()
            }
            binding.oldPassword.text?.toSet().isNullOrEmpty()
            -> {
                isEmpty = false
                Toast.makeText(requireContext(), "Enter your current Password", Toast.LENGTH_SHORT).show()
            }
        }
        return isEmpty
    }


    private fun changePassword() {

        if (binding.newPassword.text.toString() == binding.confirmPassword.text.toString()) {
            val body = ChangePasswordModel(binding.oldPassword.text.toString(),binding.newPassword.text.toString())
            viewModel.resetPassword(body)
        } else {
            Toast.makeText(
                requireContext(),
                "New Password and Confirm Password are not same",
                Toast.LENGTH_SHORT
            ).show()
        }

    }


    private fun setupViewModel() {
        val viewModelFactory =
            ViewModelFactory(Application(), repository = Repository())
        viewModel = ViewModelProvider(this, viewModelFactory).get(AuthViewModel::class.java)
    }


    private fun setObservers() {
        viewModel.resetPassword.observe(requireActivity(), {
            it.let { data ->
                val body = data.data?.body
                val message = data?.data?.message
                when (data.status) {
                    Status.SUCCESS -> {
                        if(isAdded){
                            MyApplication.hideLoader()
                            Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()
                            clearData()
                        }

                    }
                    Status.LOADING -> {
                        if(isAdded){
                            MyApplication.showLoader(requireContext())
                        }

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

    private fun clearData() {
        binding.newPassword.text.clear()
        binding.confirmPassword.text.clear()
        binding.oldPassword.text.clear()
    }


}