package com.flatrock.mymovie.adapters.viewpageradapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MainPagerAdapter(fm: FragmentManager, private var fragments: ArrayList<Fragment>?) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
{
    override fun getItem(position: Int): Fragment {
        return fragments?.get(position) ?: Fragment()
    }

    override fun getCount(): Int {
        return fragments?.size ?: 0
    }
}