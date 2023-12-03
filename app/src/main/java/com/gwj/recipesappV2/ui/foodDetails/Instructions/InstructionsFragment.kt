package com.gwj.recipesappV2.ui.foodDetails.Instructions

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gwj.recipesappV2.R
import com.gwj.recipesappV2.databinding.FragmentIngredentsBinding
import com.gwj.recipesappV2.databinding.FragmentInstructionsBinding
import com.gwj.recipesappV2.ui.base.BaseFragment
import com.gwj.recipesappV2.ui.foodDetails.FoodDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InstructionsFragment : BaseFragment<FragmentInstructionsBinding>() {
    override val viewModel: FoodDetailsViewModel by viewModels(
        ownerProducer = { requireParentFragment() } //this will use the parent viewmodel from FoodDetailsViewModel
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInstructionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUIComponents() {
        super.setupUIComponents()
        setupViewModelObserver()
    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()
        lifecycleScope.launch {
            // Collect the instructions from the ViewModel and update the TextView
            viewModel.instructions.collect { instructions ->
                binding.instructions.text = "Instructions: \n$instructions"
                    .replace(Regex("\r"), "\n")
            }
        }

        lifecycleScope.launch {
            viewModel.strYoutube.collect { youtube ->
                binding.youtubeLink.apply {
                    text = youtube // Set the click listener for the TextView
                    setOnClickListener {
                        // Create an Intent to view the YouTube link
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtube)) //open youtube link
                        startActivity(intent) // Start the Intent
                    }
                }
            }
        }

    }
}