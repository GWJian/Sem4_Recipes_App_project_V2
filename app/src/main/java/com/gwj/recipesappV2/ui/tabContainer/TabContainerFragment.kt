package com.gwj.recipesappV2.ui.tabContainer

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.gwj.recipesappV2.R
import com.gwj.recipesappV2.databinding.FragmentTabContainerBinding
import com.gwj.recipesappV2.ui.adapters.FragmentAdapter
import com.gwj.recipesappV2.ui.home.HomeFragment
import com.gwj.recipesappV2.ui.profile.ProfileFragment

class TabContainerFragment : Fragment() {

    private lateinit var binding: FragmentTabContainerBinding

    //======================== Exit App Start ========================================
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback = object : OnBackPressedCallback(true /* enabled by default */) {
            override fun handleOnBackPressed() {
                // Show dialog to confirm exit app
                AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.exit_app))
                    .setMessage(getString(R.string.are_you_sure))
                    .setPositiveButton(getString(R.string.yes)) { _, _ ->
                        // Exit the app
                        activity?.finish()
                    }
                    .setNegativeButton(getString(R.string.no), null)
                    .show()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }
    //======================== Exit App End ========================================

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTabContainerBinding.inflate(inflater, container, false)
        return binding.root
    }


    //======================== TabLayout Start ========================================
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vpContainer.adapter = FragmentAdapter(
            this,
            listOf(HomeFragment(), ProfileFragment())
        )

        TabLayoutMediator(binding.tlTabs, binding.vpContainer) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = getString(R.string.home)
                    tab.setIcon(R.drawable.ic_home)
                }

                else -> {
                    tab.text = getString(R.string.profile)
                    tab.setIcon(R.drawable.ic_person)
                }
            }
        }.attach()
    }
    //======================== TabLayout End ========================================

}