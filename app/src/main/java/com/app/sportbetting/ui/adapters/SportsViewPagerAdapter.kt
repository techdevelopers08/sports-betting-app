package com.app.sportbetting.ui.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.app.sportbetting.ui.home.CreateCompForm1Fragment
import com.app.sportbetting.ui.home.CreateCompForm2Fragment
import com.app.sportbetting.ui.home.CreateCompFragment3
import com.app.sportbetting.ui.home.SelectSportsOptionsFragment
import com.app.sportbetting.utils.Constants

class SportsViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    val list = arrayListOf<String>("Rugby,NRL,AFL,NFL,NBA,Soccer")

    override fun getCount(): Int = 4

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                val bundle = Bundle()
                bundle.putStringArrayList(Constants.BUNDLE_SPORTS_OPTIONS,list)
                val frag = SelectSportsOptionsFragment()
                frag.arguments = bundle
                return frag
            }
            1 -> {
                return CreateCompForm1Fragment()
            }
            2 -> {
                return CreateCompForm2Fragment()
            }
            3 -> {
                return CreateCompFragment3()
            }
            else->{
                return CreateCompForm1Fragment()
            }
        }
    }

}