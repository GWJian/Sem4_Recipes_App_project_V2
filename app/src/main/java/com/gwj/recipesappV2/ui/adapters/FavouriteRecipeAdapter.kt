package com.gwj.recipesappV2.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gwj.recipesappV2.data.model.favouriteRecipe
import com.gwj.recipesappV2.databinding.LayoutFavouriteRecipeBinding

// Adapter class for the RecyclerView that displays the list of favourite recipes
class FavouriteRecipeAdapter(
    private var recipes: List<favouriteRecipe>
) : RecyclerView.Adapter<FavouriteRecipeAdapter.FavouriteRecipeViewHolder>() {
    var listener: Listener? = null // Listener for click events on the recipe items

    // create a new ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteRecipeViewHolder {
        // Inflate the layout for the recipe item
        val binding = LayoutFavouriteRecipeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        // Return a new ViewHolder
        return FavouriteRecipeViewHolder(binding)
    }

    // get the number of items in the list
    override fun getItemCount() = recipes.size

    // bind the data to the ViewHolder
    override fun onBindViewHolder(holder: FavouriteRecipeViewHolder, position: Int) {
        // Get the recipe at the current position
        val favouriteItem = recipes[position]
        // Bind the recipe data to the ViewHolder
        holder.bind(favouriteItem)
    }

    // set the recipes list of the adapter
    fun setFavourite(recipes:List<favouriteRecipe>){
        this.recipes = recipes
        notifyDataSetChanged()
    }

    // ViewHolder class for the recipe item
    inner class FavouriteRecipeViewHolder(
        private val binding: LayoutFavouriteRecipeBinding // Binding for the recipe item layout
    ) : RecyclerView.ViewHolder(binding.root) {

        // bind the data to the recipe item layout
        fun bind(recipe: favouriteRecipe) {
            binding.run {
                tvMealName.text = recipe.strMeal // Set the meal name

                Glide.with(itemView) // Load the meal image
                    .load(recipe.strMealThumb)
                    .into(ivMealImage)

                mcMeals.setOnClickListener { // Set the click listener for the recipe item
                    listener?.onClick(recipe) // Call the onClick function of the listener
                }

            }
        }
    }

    interface Listener {
        fun onClick(recipe: favouriteRecipe) // handle the click event on the recipe item
    }

}