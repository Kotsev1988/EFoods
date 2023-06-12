package com.example.main.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.main.databinding.KitchenItemBinding
import com.example.main.presentation.view.list.IMyListKitchen
import com.example.main.presentation.view.IMyKitchenView
import com.example.core.domain.image.ILoadImage
import javax.inject.Inject

class MainAdapter(var myKitchen: IMyListKitchen) :
    RecyclerView.Adapter<MainAdapter.KitchenViewHolder>() {

    @Inject
    lateinit var imageLoader: ILoadImage<ImageView>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KitchenViewHolder {
        return KitchenViewHolder(
            KitchenItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            itemView.setOnClickListener {
                myKitchen.itemClickListener?.invoke(this)
            }
        }
    }

    override fun getItemCount(): Int = myKitchen.getCount()

    override fun onBindViewHolder(holder: KitchenViewHolder, position: Int) {
        myKitchen.bindView(holder.apply {
            pos = position
        })
    }

    inner class KitchenViewHolder(private val binding: KitchenItemBinding) :
        RecyclerView.ViewHolder(binding.root), IMyKitchenView {
        override fun setText(text: String) {
binding.frameKitschen.setOnClickListener {
    myKitchen.itemClickListener?.invoke(this)
}
            binding.kitchenName.text = text
        }

        override fun setImage(url: String) {

            imageLoader.loadImage(url, binding.kitchenImage)
            binding.kitchenImage.setOnClickListener {
                myKitchen.itemClickListener?.invoke(this)
            }
        }

        override var pos: Int = -1
    }

}