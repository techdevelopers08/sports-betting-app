package com.app.sportbetting.ui.auth

import android.annotation.SuppressLint
import android.app.Application
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.app.sportbetting.network.Repository
import com.app.sportbetting.MainActivity
import com.app.sportbetting.R
import com.app.sportbetting.databinding.ActivityRegisterBinding
import com.app.sportbetting.models.register.RegisterModel
import com.app.sportbetting.models.timezone.Body
import com.app.sportbetting.utils.*
import com.app.sportbetting.viewmodel.AuthViewModel
import com.app.sportbetting.viewmodel.ViewModelFactory
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityRegisterBinding
    var preferences: SharedPreference? = null
    lateinit var timezoneList: ArrayList<String>

    lateinit var viewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        setObservers()
    }

    private fun setSpinners() {
        ArrayAdapter.createFromResource(
            this,
            R.array.spinnerItems,
            R.layout.text_view
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.favSports.adapter = adapter
        }
       /* ArrayAdapter.createFromResource(
            this,
            R.array.gmt,
            R.layout.text_view
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.timeZone.adapter = adapter
        }*/
        ArrayAdapter.createFromResource(
            this,
            R.array.team_spinner,
            R.layout.text_view
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.favTeam.adapter = adapter
        }

    }


    private fun setObservers() {
        viewModel.registerResult.observe(this) {
            it.let { data ->
                val message = data?.data?.message
                val body = data?.data?.body

                when (data.status) {
                    Status.SUCCESS -> {
                        MyApplication.hideLoader()
                        Log.d("TAG", "setObservers: ${body?.token}")
                        preferences?.put(
                            SharedPreference.Key.PASSWORD,
                            binding.tippingEdit.text.toString()
                        )
                        preferences?.put(SharedPreference.Key.TOKEN, body?.token)
                        preferences?.put(SharedPreference.Key.USERID, body?.userDetails?.id!!)
                        preferences?.put(SharedPreference.Key.ISUSERLOGIN, true)
                        preferences?.put(SharedPreference.Key.EMAIL, body?.userDetails?.email)
                        preferences?.put(SharedPreference.Key.USERNAME, body?.userDetails?.name)
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                    Status.LOADING -> {
                        MyApplication.showLoader(this)
                    }
                    Status.ERROR -> {
                        MyApplication.hideLoader()
                        Toast.makeText(
                            this,
                            it.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        viewModel.timezoneResult.observe(this) {
            it.let { data ->
                val message = data?.data?.message
                val body = data?.data?.body

                when (data.status) {
                    Status.SUCCESS -> {
                        MyApplication.hideLoader()
                        setTimezoneSpinner(body)
                    }
                    Status.LOADING -> {
                        MyApplication.showLoader(this)
                    }
                    Status.ERROR -> {
                        MyApplication.hideLoader()
                        Toast.makeText(
                            this,
                            it.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun setTimezoneSpinner(body: List<Body>?) {
        timezoneList.clear()
        body?.forEach{ it ->
            timezoneList.add(it.timezone)
        }

        binding.timeZone.adapter = ArrayAdapter(
           this,
            R.layout.text_view,
            timezoneList.toArray()
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.timeZone.adapter = adapter
        }
    }

    var dd = ""
    var mm = ""
    var yy = ""

    @SuppressLint("SetTextI18n")
    private fun setDob() {
        val c: Calendar = Calendar.getInstance()
        val mYear = c.get(Calendar.YEAR)
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, day ->
                if ((month+1).toString().length == 1 && day.toString().length == 1) {
                    val dd = day.toString()
                    val mm = (month + 1).toString()
                    val yy = year.toString()

                    binding.textView12.text = "$year/0${month + 1}/0$day"
                } else if ((month+1).toString().length == 1) {
                    dd = day.toString()
                    mm = (month + 1).toString()
                    yy = year.toString()

                    binding.textView12.text = "$year/0${month + 1}/$day"
                } else if (day.toString().length == 1) {
                    dd = day.toString()
                    mm = (month + 1).toString()
                    yy = year.toString()

                    binding.textView12.text = "$year/${month + 1}/0$day"
                } else {
                    dd = day.toString()
                    mm = (month + 1).toString()
                    yy = year.toString()

                    binding.textView12.text = "$year/${month + 1}/$day"
                }
            }, mYear, mMonth, mDay
        )

        datePickerDialog.show()
    }


    private fun checkValidation() {
        val selectedId = binding.gender.checkedRadioButtonId
        val radioButton = findViewById<RadioButton>(selectedId)

        if (Utility.isInternetConnected(this)) {
            if (isFieldEmpty()) {
                if (isEmailValid()) {

                        if (radioButton != null) {
                            val body = RegisterModel(
                                binding.tippingEdit.text.toString(),
                                binding.emailAddress.text.toString(),
                                binding.firstName.text.toString(),
                                binding.surname.text.toString(),
                                binding.textView12.text.toString(),
                                radioButton.text.toString(),
                                binding.country.text.toString(),
                                binding.favSports.selectedItem.toString(),
                                binding.postCode.text.toString(),
                                binding.favTeam.selectedItem.toString(),
                                "GMT",
                                binding.password.text.toString(),
                            )

                            viewModel.onRegister(body)
                        }

                }
            } else {
                this.toast("Invalid Email")
            }

        } else {
            this.toast("Unable to connect to Internet")
        }
    }


    private fun isEmailValid(): Boolean = Pattern.matches(
        Patterns.EMAIL_ADDRESS.toRegex().toString(),
        binding.emailAddress.text.toString()
    )

    private fun isFieldEmpty(): Boolean {
        var isEmpty = true
        when {
            binding.emailAddress.text?.toString().isNullOrBlank() -> {
                isEmpty = false
                this.toast("Enter email")
            }
            binding.firstName.text?.toString().isNullOrBlank() -> {
                isEmpty = false
                Toast.makeText(this, "Enter Your First name", Toast.LENGTH_SHORT).show()
            }

            binding.confirmEmail.text.toString().isNullOrBlank() -> {
                isEmpty = false
                Toast.makeText(this, "Enter Confirm email", Toast.LENGTH_SHORT).show()
            }

            binding.password.text.toString().isNullOrBlank() -> {
                isEmpty = false
                Toast.makeText(this, "Enter your Password", Toast.LENGTH_SHORT).show()
            }

            binding.surname.text.toString().isNullOrBlank() -> {
                isEmpty = false
                Toast.makeText(this, "Enter Your surname", Toast.LENGTH_SHORT).show()
            }
            binding.postCode.text.toString().isNullOrBlank() -> {
                isEmpty = false
                Toast.makeText(this, "Enter Your PostCode", Toast.LENGTH_SHORT).show()
            }
            binding.tippingEdit.text.toString().isNullOrBlank() -> {
                isEmpty = false
                Toast.makeText(this, "Enter Your username (Tipping name)", Toast.LENGTH_SHORT)
                    .show()
            }
            !binding.checkBox.isChecked -> {
                isEmpty = false
                this.toast("Please check all the checkboxes")
            }
            !binding.checkBox2.isChecked -> {
                isEmpty = false
                this.toast("Please check all the checkboxes")
            }
            !binding.checkBox3.isChecked -> {
                isEmpty = false
                this.toast("Please check all the checkboxes")
            }
            binding.country.text.toString().isNullOrBlank() -> {
                isEmpty = false
                this.toast("Please Enter your Country")
            }

        }
        return isEmpty
    }


    private fun isValidPassword(password: String?): Boolean {

        // Regex to check valid password.
        val regex =
            ("^(?=.*[0-9])"                     //represents a digit must occur at least once
                    + "(?=.*[a-z])(?=.*[A-Z])"              //represents a lower case and an upper case alphabet must occur at least once.
                    + "(?=.*[@#$%^&+=])"                    //represents a special character that must occur at least once..
                    + "(?=\\S+$).{8,20}$")                  //represents no white spaces and at least 8 characters and at most 20 characters.


        val p = Pattern.compile(regex)

        val m: Matcher = p.matcher(password)

        return m.matches()
    }


    private fun initViews() {
        preferences = SharedPreference.getInstance(this)
        setupViewModel()
        setSpinners()
        binding.button4.setOnClickListener(this)
        binding.calendar.setOnClickListener(this)

    }


    private fun setupViewModel() {
        val viewModelFactory =
            ViewModelFactory(Application(), repository = Repository())
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(AuthViewModel::class.java)
    }


    override fun onClick(v: View?) {
        when (v) {
            binding.calendar -> {
                setDob()
            }
            binding.button4 -> {
                checkValidation()
            }
        }
    }
}