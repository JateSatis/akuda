package com.example.akuda.screens.account

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class PostsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        return PostsFragment.newInstance(position)
    }

    override fun getItemCount(): Int {
        return 2
    }
}