package com.example.mycard.presentation.adapter

import android.text.SpannableString
import android.text.Spanned
import android.text.style.BulletSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mycard.databinding.ItemMyCardBinding
import com.example.mycard.presentation.view.IMyCardDishesView
import com.example.mycard.presentation.view.list.IListMyCardDishesView


class MyCardAdapter(var myCard: IListMyCardDishesView) :
    RecyclerView.Adapter<MyCardAdapter.MyProductsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyProductsViewHolder {
        return MyProductsViewHolder(
            ItemMyCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = myCard.getCount()

    fun setData(myCardNew: IListMyCardDishesView){
        myCard = myCardNew
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: MyProductsViewHolder, position: Int) {
        myCard.bindView(holder.apply {
            pos = position
        })
    }

    inner class MyProductsViewHolder(private val binding: ItemMyCardBinding) :
        RecyclerView.ViewHolder(binding.root), IMyCardDishesView {

        override fun setText(text: String) {
            binding.productName.text = text
        }

        override fun setPrice(price: String) {
            binding.productPrice.text = price + " Р"
        }

        override fun setWeght(weight: String) {
            val string  = SpannableString("$weight г")
            string.setSpan(BulletSpan(10), 0, string.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)


            binding.productWeight.text = string
        }

        override fun loadAvatar(url: String) {
            Glide.with(binding.product.context).load(url).into(binding.product)
        }

        override fun setCounter(counter: String) {
            binding.score1.text = counter
            binding.decreaseTeam1.setOnClickListener {
                myCard.itemClickListenerDecrease?.invoke(this)
            }

            binding.increaseTeam1.setOnClickListener {
                myCard.itemClickListenerIncrease?.invoke(this)
            }
        }

        override var pos: Int = -1
    }

}