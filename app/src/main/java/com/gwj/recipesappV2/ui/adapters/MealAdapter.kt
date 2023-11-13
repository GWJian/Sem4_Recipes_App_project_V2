package com.gwj.recipesappV2.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.animation.AnimatableView.Listener
import com.gwj.recipesappV2.data.model.Meal
import com.gwj.recipesappV2.databinding.LayoutMealsItemBinding

class MealAdapter(
    private var meals: List<Meal>
) : RecyclerView.Adapter<MealAdapter.MealsItemViewHolder>() {

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsItemViewHolder {
        val binding = LayoutMealsItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MealsItemViewHolder(binding)
    }

    override fun getItemCount() = meals.size

    override fun onBindViewHolder(holder: MealsItemViewHolder, position: Int) {
        val item = meals[position]
        holder.bind(item)
    }

    fun setMeals(items: List<Meal>) {
        meals = items
        notifyDataSetChanged()
    }

    inner class MealsItemViewHolder(
        private val binding: LayoutMealsItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(meal: Meal) {
            binding.run {
                tvMealName.text = meal.strMeal

                Glide.with(binding.root)
                    .load(meal.strMealThumb)
                    .into(ivMealImg)

                mcMealCV.setOnClickListener {
                    listener?.onClick(meal)
                }
            }
        }
    }

    interface Listener {
        fun onClick(meal: Meal)
    }
}