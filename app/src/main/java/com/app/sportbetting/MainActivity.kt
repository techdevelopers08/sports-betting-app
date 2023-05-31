package com.app.sportbetting

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.app.sportbetting.databinding.ActivityMainBinding
import com.app.sportbetting.network.Repository
import com.app.sportbetting.ui.auth.SignInActivity
import com.app.sportbetting.ui.home.*
import com.app.sportbetting.utils.MyApplication
import com.app.sportbetting.utils.SharedPreference
import com.app.sportbetting.utils.Status
import com.app.sportbetting.viewmodel.AuthViewModel
import com.app.sportbetting.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val TAG = "MainActivity"
    private lateinit var binding : ActivityMainBinding
    private lateinit var navController: NavController
    lateinit var viewModel:AuthViewModel
    private var preference:SharedPreference?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initViews()
        setObservers()
    }

    private fun initViews(){

        setupViewModel()
        preference = SharedPreference.getInstance(this)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fcView) as NavHostFragment
        navController = navHostFragment.navController

        binding.navView.setupWithNavController(navController)

        binding.ivDrawer.setOnClickListener(this)
        binding.ivBack.setOnClickListener(this)
        binding.layoutNav.myAccount.setOnClickListener(this)
        binding.layoutNav.myComp.setOnClickListener(this)
        binding.layoutNav.createComp.setOnClickListener(this)
        binding.layoutNav.leaderBoard.setOnClickListener(this)
        binding.layoutNav.joinComp.setOnClickListener(this)
        binding.layoutNav.tipping.setOnClickListener(this)
        binding.layoutNav.more.setOnClickListener(this)
        binding.layoutNav.logout.setOnClickListener(this)
        binding.layoutNav.resetPassword.setOnClickListener(this)

    }

    private fun setupViewModel() {
        val viewModelFactory =
            ViewModelFactory(Application(), repository = Repository())
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(AuthViewModel::class.java)
    }



    private fun setObservers(){
        viewModel.logoutResult.observe(this, Observer {
            it.let { data ->
                val body = data.data?.body
                val message = data?.data?.message
                when (data.status) {
                    Status.SUCCESS -> {
                        MyApplication.hideLoader()
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                        preference?.put(SharedPreference.Key.ISUSERLOGIN, false)
                        startActivity(Intent(this, SignInActivity::class.java))
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
        })

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onClick(v: View?) {
        when(v){
            binding.ivDrawer->{
                binding.drawerLayout.openDrawer(GravityCompat.END)
            }
            binding.ivBack->{
                onBackPressed()
            }
            binding.layoutNav.myAccount->{
//                findNavController(R.id.fcView).navigate(R.id.action_homeFragment_to_myAccount)
                loadFragment(MyAccount.newInstance())
                binding.drawerLayout.closeDrawer(GravityCompat.END)
            }
            binding.layoutNav.joinComp->{
                loadFragment(JoinCompFragment())
                binding.drawerLayout.closeDrawer(GravityCompat.END)

            }
            binding.layoutNav.createComp->{
                loadFragment(SelectSportsOptionsFragment())
                binding.drawerLayout.closeDrawer(GravityCompat.END)

            }
            binding.layoutNav.leaderBoard->{
                loadFragment(LeaderBoardFragment())
                binding.drawerLayout.closeDrawer(GravityCompat.END)

            }
            binding.layoutNav.myComp->{
                loadFragment(MyCompFragment())
                binding.drawerLayout.closeDrawer(GravityCompat.END)
            }

            binding.layoutNav.more->{
                loadFragment(MoreFragment())
                binding.drawerLayout.closeDrawer(GravityCompat.END)
            }

            binding.layoutNav.tipping->{
                loadFragment(TippingFragment())
                binding.drawerLayout.closeDrawer(GravityCompat.END)
            }

            binding.layoutNav.logout->{
                viewModel.logout()
            }
            binding.layoutNav.resetPassword->{
                loadFragment(ResetPassword.newInstance())
                binding.drawerLayout.closeDrawer(GravityCompat.END)
            }

            else -> {
                loadFragment(HomeFragment())
                binding.drawerLayout.closeDrawer(GravityCompat.END)
            }
        }
    }


    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fcView,fragment).addToBackStack(null).setReorderingAllowed(true
        ).commit()
    }


    override fun onBackPressed() {
        super.onBackPressed()
        binding.drawerLayout.closeDrawer(GravityCompat.END)

    }



}