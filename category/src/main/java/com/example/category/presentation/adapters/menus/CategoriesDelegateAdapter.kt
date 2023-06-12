package com.example.category.presentation.adapters.menus

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.category.databinding.ItemMenuBinding
import com.example.category.presentation.adapters.DelegateAdapter
import com.example.category.presentation.adapters.DelegateAdapterItem

class CategoriesDelegateAdapter() :
    DelegateAdapter<CategoriesDelegate, CategoriesDelegateAdapter.CategoryViewHolder>(
        CategoriesDelegate::class.java
    ) {

    private var categoryHorizontalAdapter: CategoryHorizontalAdapter? = null

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = CategoryViewHolder(
        ItemMenuBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun bindViewHolder(
        model: CategoriesDelegate,
        viewHolder: CategoryViewHolder,
        payloads: List<DelegateAdapterItem.Payloadable>,
    ) {
        viewHolder.bind(model)
    }

    inner class CategoryViewHolder(private val binding: ItemMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: CategoriesDelegate) {

            categoryHorizontalAdapter = CategoryHorizontalAdapter(model.menusListList)
            binding.recyclerView2.adapter = categoryHorizontalAdapter
        }

    }
}