package com.example.category.presentation.adapters.dishes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.category.databinding.ItemFoodBinding
import com.example.category.presentation.view.lists.IDishesList
import com.example.category.presentation.view.IDishItemView

class DishesGridAdapter(private val dishesList: IDishesList) :
    RecyclerView.Adapter<DishesGridAdapter.DishesViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): DishesGridAdapter.DishesViewHolder = DishesViewHolder(
        ItemFoodBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )
        .apply {
            itemView.setOnClickListener {
                dishesList.itemClickListener?.invoke(this)
            }
        }

    override fun onBindViewHolder(holder: DishesGridAdapter.DishesViewHolder, position: Int) {
        dishesList.bindView(holder.apply {
            pos = position
        })
    }

    override fun getItemCount(): Int = dishesList.getCount()

    inner class DishesViewHolder(private val binding: ItemFoodBinding) :
        RecyclerView.ViewHolder(binding.root),
        IDishItemView {
        override fun setText(text: String) {
            binding.foodName.text = text
        }

        override fun setImage(url: String) {

            Glide.with(binding.foodImage.context).load(url).into(binding.foodImage)
        }

        override var pos: Int = -1

    }
}