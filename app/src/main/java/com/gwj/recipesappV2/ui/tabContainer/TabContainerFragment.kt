package com.gwj.recipesappV2.ui.tabContainer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.gwj.recipesappV2.R
import com.gwj.recipesappV2.databinding.FragmentTabContainerBinding
import com.gwj.recipesappV2.ui.adapters.FragmentAdapter
import com.gwj.recipesappV2.ui.home.HomeFragment
import com.gwj.recipesappV2.ui.profile.ProfileFragment

class TabContainerFragment : Fragment() {

    private lateinit var binding: FragmentTabContainerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTabContainerBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vpContainer.adapter = FragmentAdapter(
            this,
            listOf(HomeFragment(), ProfileFragment())
        )

        TabLayoutMediator(binding.tlTabs, binding.vpContainer) { tab, position ->
            when (position) {
                0 -> {
                    //tab.text = "Home"
                    tab.setIcon(R.drawable.ic_home)
                }

                else -> {
                    //tab.text = "Profile"
                    tab.setIcon(R.drawable.ic_person)
                }
            }
        }.attach()
    }

}