package com.example.coffeeshopapp.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.coffeeshopapp.utils.CartManager
import com.example.coffeeshopapp.R
import com.example.coffeeshopapp.databinding.ActivityDetailBinding
import com.example.coffeeshopapp.model.ItemsModel

class DetailActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var item: ItemsModel
    private lateinit var context: Context
    private var selectedSize: String = "S" // Default size selection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        context = this

        bundle()

        // Handle Add to Cart buttons
        binding.cartButton.setOnClickListener { handleCartButtonClick() }
        binding.cartBtn.setOnClickListener { handleCartButtonClick() }
    }

    private fun handleCartButtonClick() {
        val newItem = ItemsModel(
            id = "${item.id}_${System.currentTimeMillis()}", // Unique ID for each added product
            title = item.title + " ($selectedSize)", // Append selected size
            description = item.description,
            picUrl = item.picUrl,
            price = item.price,
            extra = item.extra,
            categoryId = item.categoryId,
            quantity = 1,
            size = selectedSize  // Use selected size

        )

        // Add the unique item to the cart
        CartManager.addToCart(newItem)

        Log.d("CartDebug", "${newItem.title} added to cart with size: $selectedSize") // Debugging log
        Toast.makeText(this, "${newItem.title} added to cart!", Toast.LENGTH_SHORT).show()

        // Navigate to AddToCartActivity
        startActivity(Intent(this, AddToCartActivity::class.java))
    }

    private fun bundle() {
        binding.apply {
            item = intent.getParcelableExtra("object", ItemsModel::class.java)!!
            titleText.text = item.title
            descriptionText.text = item.description
            subtitleText.text = item.extra
            priceText.text = "$" + item.price

            Glide.with(context)
                .load(item.picUrl[0])
                .apply(RequestOptions.bitmapTransform(com.bumptech.glide.load.resource.bitmap.RoundedCorners(100)))
                .into(binding.imgDetail)

            backBtn.setOnClickListener { finish() }

            sizeBtn1.setOnClickListener {
                selectedSize = "S" // Update selected size
                updateSizeSelection()
            }

            sizeBtn2.setOnClickListener {
                selectedSize = "M" // Update selected size
                updateSizeSelection()
            }

            sizeBtn3.setOnClickListener {
                selectedSize = "L" // Update selected size
                updateSizeSelection()
            }
        }
    }

    private fun updateSizeSelection() {
        binding.apply {
            sizeBtn1.setBackgroundDrawable(ContextCompat.getDrawable(context, if (selectedSize == "S") R.drawable.orange_stroke_bg else R.drawable.dark_grey_bg2))
            sizeBtn2.setBackgroundDrawable(ContextCompat.getDrawable(context, if (selectedSize == "M") R.drawable.orange_stroke_bg else R.drawable.dark_grey_bg2))
            sizeBtn3.setBackgroundDrawable(ContextCompat.getDrawable(context, if (selectedSize == "L") R.drawable.orange_stroke_bg else R.drawable.dark_grey_bg2))

            sizeBtn1.setTextColor(ContextCompat.getColor(context, if (selectedSize == "S") R.color.orange else R.color.white))
            sizeBtn2.setTextColor(ContextCompat.getColor(context, if (selectedSize == "M") R.color.orange else R.color.white))
            sizeBtn3.setTextColor(ContextCompat.getColor(context, if (selectedSize == "L") R.color.orange else R.color.white))
        }
    }
}
