package com.gwj.recipesappV2.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gwj.recipesappV2.databinding.FragmentInstructionsBinding

class InstructionsAdapter(
    private var instructions: List<String>
) : RecyclerView.Adapter<InstructionsAdapter.InstructionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstructionViewHolder {
        val binding = FragmentInstructionsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return InstructionViewHolder(binding)
    }

    override fun getItemCount() = instructions.size

    override fun onBindViewHolder(holder: InstructionViewHolder, position: Int) {
        val instructions = instructions[position]
        holder.bind(instructions)
    }

    fun instructionData(items: List<String>) {
        instructions = items
        //Log.d("debugging_InstructionsAdapter", "ingredientData: $instructions")
        notifyDataSetChanged()
    }

    inner class InstructionViewHolder(
        private val binding: FragmentInstructionsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(instructions: String) {
            binding.run {
                tvInstructions.text = instructions
            }
        }
    }
}