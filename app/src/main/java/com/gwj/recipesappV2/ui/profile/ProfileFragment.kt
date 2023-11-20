package com.gwj.recipesappV2.ui.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.gwj.recipesappV2.R
import com.gwj.recipesappV2.databinding.FragmentProfileBinding
import com.gwj.recipesappV2.ui.adapters.FavouriteRecipeAdatper
import com.gwj.recipesappV2.ui.base.BaseFragment
import com.gwj.recipesappV2.ui.base.BaseViewModel
import com.gwj.recipesappV2.ui.tabContainer.TabContainerFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override val viewModel: ProfileViewModel by viewModels()
    lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>
    private lateinit var favouriteRecipeAdatper: FavouriteRecipeAdatper

    //============================ Personal Image Start ============================
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Registers a photo picker activity launcher in single-select mode.
        pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            // Callback is invoked after the user selects a media item or closes the
            // photo picker.
            if (uri != null) {
                Log.d("PhotoPicker", "Selected URI: $uri")
                viewModel.updateProfilePic(uri)
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }
    }
    //============================ Personal Image End ============================


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUIComponents() {
        super.setupUIComponents()
        favouriteRecipeAdatper()

        //================== Logout Btn Start ============================
        binding.ivLogout.setOnClickListener {
            viewModel.logout()
        }
        //================== Logout Btn End ============================

        //================== Choose Img Btn Start ======================
        binding.icEditProfile.setOnClickListener {
            // Launch the photo picker and let the user choose only images.
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        //================== Choose Img Btn Start ======================

    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()

        lifecycleScope.launch {
            viewModel.finish.collect {
                val action = TabContainerFragmentDirections.toLogin()
                navController.navigate(action)
            }
        }

        lifecycleScope.launch {
            viewModel.user.collect {
                binding.tvEmail.text = it.email
                binding.tvName.text = it.name
            }
        }

        lifecycleScope.launch {
            viewModel.profileUri.collect {
                Glide.with(requireContext())
                    .load(it)
                    .placeholder(R.drawable.ic_person)
                    .into(binding.ivProfile)
            }
        }

    }

    private fun favouriteRecipeAdatper() {
        favouriteRecipeAdatper = FavouriteRecipeAdatper(emptyList())

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvRecipe.adapter = favouriteRecipeAdatper
        binding.rvRecipe.layoutManager = layoutManager
    }


}