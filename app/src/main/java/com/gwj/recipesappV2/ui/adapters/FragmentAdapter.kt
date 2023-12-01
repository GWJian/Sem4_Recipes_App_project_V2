package com.gwj.recipesappV2.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

// Adapter class for managing fragments in a ViewPager2
class FragmentAdapter(
    fragment: Fragment, // Fragment that contains the ViewPager2
    private val tabs: List<Fragment>, // List of fragments to display in the ViewPager2
) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = tabs.size // Return the number of fragments

    // Create a new fragment for the specified position
    override fun createFragment(position: Int): Fragment {
        return tabs[position] // Return the fragment at the specified position
    }
}