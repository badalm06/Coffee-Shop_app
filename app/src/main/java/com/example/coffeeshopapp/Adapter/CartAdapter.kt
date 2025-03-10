package com.example.coffeeshopapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coffeeshopapp.utils.CartManager
import com.example.coffeeshopapp.databinding.ItemCartBinding
import com.example.coffeeshopapp.model.ItemsModel

class CartAdapter(private val cartItems: MutableList<ItemsModel>) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(private val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ItemsModel) {
            binding.titleText.text = item.title
            binding.priceText.text = "$${item.price} (${item.size})"
            binding.quantityText.text = item.quantity.toString()

            // Ensure the image list is not empty before accessing it
            if (item.picUrl.isNotEmpty()) {
                Glide.with(binding.root.context)
                    .load(item.picUrl[0])
                    .into(binding.imgCart)
            }

            // Increase quantity
            binding.btnIncrease.setOnClickListener {
                item.quantity++
                `CartManager`.updateQuantity(item.id, item.quantity)
                binding.quantityText.text = item.quantity.toString()
                notifyDataSetChanged() // Update RecyclerView
            }

            // Decrease quantity
            binding.btnDecrease.setOnClickListener {
                if (item.quantity > 1) {
                    item.quantity--
                    CartManager.updateQuantity(item.id, item.quantity)
                    binding.quantityText.text = item.quantity.toString()
                } else {
                    CartManager.removeItem(item.id)

                    // Remove item from cartItems list in Adapter
                    cartItems.removeAt(adapterPosition)

                    // Notify RecyclerView about item removal
                    notifyItemRemoved(adapterPosition)
                }
                notifyDataSetChanged() // Refresh the RecyclerView
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }

    override fun getItemCount(): Int = cartItems.size
}
