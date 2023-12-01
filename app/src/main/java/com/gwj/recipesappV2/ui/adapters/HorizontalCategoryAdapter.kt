package com.gwj.recipesappV2.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.gwj.recipesappV2.R
import com.gwj.recipesappV2.data.model.Category
import com.gwj.recipesappV2.databinding.HorizontalLayoutCategoriesItemBinding

class HorizontalCategoryAdapter(
    private var categories: List<Category>, // List of categories
    private var onCategoryClick: (Category) -> Unit, // call when a category is clicked
) : RecyclerView.Adapter<HorizontalCategoryAdapter.CategoryOnClickViewHolder>() {

    private var selectedCatId: String? = null // track the selected category id selected by user

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HorizontalCategoryAdapter.CategoryOnClickViewHolder {
        val binding = HorizontalLayoutCategoriesItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryOnClickViewHolder(binding)
    }

    override fun getItemCount() = categories.size

    override fun onBindViewHolder(holder: CategoryOnClickViewHolder, position: Int) {
        val item = categories[position]
        holder.bind(item)
    }

    // update the list of categories
    fun setCategories(items: List<Category>) {
        categories = items
        notifyDataSetChanged()
    }

    //to set the background color of the selected category
    fun setSelectedCategory(id: String) {
        selectedCatId = id
        notifyDataSetChanged()
    }

    //This allows user to click on the category and get the meals from that category
    inner class CategoryOnClickViewHolder(
        private val binding: HorizontalLayoutCategoriesItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.run {
                tvCategories.text = category.strCategory // Set the category name

                //if idCategory same as selectedCatId, then set the background color to green
                if (category.idCategory == selectedCatId) {
                    binding.llCategory.setBackgroundColor(Color.parseColor("#0BDD20"))
                } else {
                    // else back to #00000000
                    binding.llCategory.setBackgroundColor(Color.parseColor("#00000000"))
                }

                // Set the click listener for the category item
                itemView.setOnClickListener {
                    onCategoryClick(category)
                }

            }
        }
    }
}