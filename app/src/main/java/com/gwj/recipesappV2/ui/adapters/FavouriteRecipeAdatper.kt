package com.gwj.recipesappV2.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gwj.recipesappV2.data.model.FavoriteRecipe
import com.gwj.recipesappV2.databinding.LayoutFavouriteRecipeBinding

class FavouriteRecipeAdatper(
    private var recipes: List<FavoriteRecipe>
) : RecyclerView.Adapter<FavouriteRecipeAdatper.FavouriteRecipeViewHolder>() {
    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteRecipeViewHolder {
        val binding = LayoutFavouriteRecipeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavouriteRecipeViewHolder(binding)
    }

    override fun getItemCount() = recipes.size

    override fun onBindViewHolder(holder: FavouriteRecipeViewHolder, position: Int) {
        val favouriteItem = recipes[position]
        holder.bind(favouriteItem)
    }

    fun setFavourite(recipes:List<FavoriteRecipe>){
        this.recipes = recipes
        notifyDataSetChanged()
    }

    inner class FavouriteRecipeViewHolder(
        private val binding: LayoutFavouriteRecipeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: FavoriteRecipe) {
            binding.run {
                tvMealName.text = recipe.strMeal

                Glide.with(itemView)
                    .load(recipe.strMealThumb)
                    .into(ivMealImage)

                mcMeals.setOnClickListener {
                    listener?.onClick(recipe)
                }

            }
        }
    }

    interface Listener {

        fun onClick(recipe: FavoriteRecipe)
    }

}