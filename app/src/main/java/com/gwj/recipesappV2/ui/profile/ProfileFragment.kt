package com.gwj.recipesappV2.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.gwj.recipesappV2.R
import com.gwj.recipesappV2.data.model.favouriteRecipe
import com.gwj.recipesappV2.databinding.FragmentProfileBinding
import com.gwj.recipesappV2.ui.adapters.FavouriteRecipeAdapter
import com.gwj.recipesappV2.ui.base.BaseFragment
import com.gwj.recipesappV2.ui.tabContainer.TabContainerFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override val viewModel: ProfileViewModel by viewModels()
    lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>
    private lateinit var favouriteRecipeAdapter: FavouriteRecipeAdapter

    //============================ Personal Image Start ============================
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Registers a photo picker activity launcher in single-select mode.
        pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            // Callback is invoked after the user selects a media item or closes the
            // photo picker.
            if (uri != null) {
                //Log.d("PhotoPicker", "Selected URI: $uri")
                viewModel.updateProfilePic(uri)
            } else {
                //do nothing if close the picker
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
        favouriteRecipeAdapter()

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

    //===================== Favourite Adapter Start =======================
    private fun favouriteRecipeAdapter() {
        favouriteRecipeAdapter = FavouriteRecipeAdapter(emptyList())
        favouriteRecipeAdapter.listener = object : FavouriteRecipeAdapter.Listener {
            override fun onClick(recipe: favouriteRecipe) {
                val action =
                    TabContainerFragmentDirections.actionTabContainerToFoodDetails(recipe.strMeal, recipe.idMeal)
                navController.navigate(action)
            }
        }

        val staggeredLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.rvRecipe.adapter = favouriteRecipeAdapter
        binding.rvRecipe.layoutManager = staggeredLayoutManager

    }
    //===================== Favourite Adapter End =======================

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()

        lifecycleScope.launch {
            viewModel.finish.collect {
                val action = TabContainerFragmentDirections.toLogin()
                navController.navigate(action)
            }
        }

        //=============== Load Profile Data Start =====================
        lifecycleScope.launch {
            viewModel.user.collect {
                binding.tvEmail.text = it.email
                binding.tvName.text = it.name.capitalize()
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
        //=============== Load Profile Data End =====================

        //===================== Show Favourite Adapter Start =======================
        lifecycleScope.launch {
            viewModel.favourite.collect {
                favouriteRecipeAdapter.setFavourite(it)
            }
        }
        //===================== Show Favourite Adapter End =======================

    }


}