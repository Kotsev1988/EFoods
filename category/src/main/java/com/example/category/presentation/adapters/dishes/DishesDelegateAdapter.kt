package com.example.category.presentation.adapters.dishes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.category.databinding.ItemFoodsBinding
import com.example.category.presentation.adapters.DelegateAdapter
import com.example.category.presentation.adapters.DelegateAdapterItem

class DishesDelegateAdapter :
    DelegateAdapter<DishesDelegate, DishesDelegateAdapter.DishesViewHolder>(
        DishesDelegate::class.java
    ) {

    private var dishesGridAdapter: DishesGridAdapter? = null

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = DishesViewHolder(
        ItemFoodsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun bindViewHolder(
        model: DishesDelegate,
        viewHolder: DishesViewHolder,
        payloads: List<DelegateAdapterItem.Payloadable>,
    ) {
        viewHolder.bind(model)
    }


    inner class DishesViewHolder(private val binding: ItemFoodsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: DishesDelegate) {

            dishesGridAdapter = model.dishesList?.let { DishesGridAdapter(it) }
            binding.dishesRecycle.adapter = dishesGridAdapter
        }

    }
}