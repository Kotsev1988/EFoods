package com.example.category.presentation.adapters.menus

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.category.R
import com.example.category.databinding.ItemMenuNameBinding
import com.example.category.presentation.view.lists.IListMenu
import com.example.category.presentation.view.IMenuItemView

class CategoryHorizontalAdapter(private val categoryList: IListMenu): RecyclerView.Adapter<CategoryHorizontalAdapter.CategoryHorizontalViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CategoryHorizontalViewHolder = CategoryHorizontalViewHolder(ItemMenuNameBinding.inflate(
        LayoutInflater.from(parent.context), parent, false)).apply {
            categoryList.itemClickListener?.invoke(this)
    }

    override fun getItemCount(): Int = categoryList.getCount()

    override fun onBindViewHolder(holder: CategoryHorizontalViewHolder, position: Int) {
        categoryList.bindView(holder.apply {
            pos = position
        })
    }

    inner class CategoryHorizontalViewHolder(private val binding: ItemMenuNameBinding):RecyclerView.ViewHolder(binding.root),
        IMenuItemView{

        override var pos: Int=0

        override fun clickButton() {
            binding.categoryMenuName.setOnClickListener {
                categoryList.itemClickListener?.invoke(this)
                notifyDataSetChanged()
            }
        }

        override fun setText(text: String) {
            binding.categoryMenuName.text = text
        }

        override fun changeColor(pos: Int) {
            if (pos == 0){
                binding.categoryMenuName.isSelected = true
               // binding.categoryMenuName.setBackgroundColor(Color.parseColor("#FF6E4E"))
            }else{
                binding.categoryMenuName.isSelected = false
               // binding.categoryMenuName.setBackgroundColor(Color.parseColor("#F8F7F5"))
            }
        }


    }
}