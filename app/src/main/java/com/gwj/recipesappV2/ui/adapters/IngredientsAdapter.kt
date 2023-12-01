package com.gwj.recipesappV2.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gwj.recipesappV2.databinding.LayoutIngredientsItemBinding

class IngredientsAdapter(
    private var ingredients: List<Pair<String, String>>
) : RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val binding = LayoutIngredientsItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return IngredientViewHolder(binding)
    }

    override fun getItemCount() = ingredients.size

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient = ingredients[position]
        holder.bind(ingredient)
    }

    fun ingredientData(items: List<Pair<String, String>>) {
        ingredients = items
        //Log.d("debugging_IngredientsAdapter", "ingredientData: $ingredients")
        notifyDataSetChanged() //this will update the data
    }

    inner class IngredientViewHolder(
        private val binding: LayoutIngredientsItemBinding // Binding for the recipe item layout
    ) : RecyclerView.ViewHolder(binding.root) {

        // bind the ingredient data to the layout
        fun bind(ingredient: Pair<String, String>) {
            binding.run {
                tvIngredientsName.text = ingredient.first // Set the ingredient name
                tvIngredientMeasure.text = ingredient.second // Set the ingredient measure

                // Construct the URL for the ingredient image
                val imageUrl =
                    "https://www.themealdb.com/images/ingredients/${ingredient.first}.png"
                // Load the ingredient image
                Glide.with(binding.root)
                    .load(imageUrl)
                    .into(ivIngredients)
            }
        }
    }
}

