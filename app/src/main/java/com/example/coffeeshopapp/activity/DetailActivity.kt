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

        // 🛒 Navigate to Cart when clicking on cart button
        binding.cartButton.setOnClickListener {
            startActivity(Intent(this, AddToCartActivity::class.java))
        }

        // ➕ Add to Cart button action
        binding.cartBtn.setOnClickListener { handleCartButtonClick() }

        // ✅ Buy Now button ➝ Directly go to Checkout
        binding.BuyBtn.setOnClickListener { handleBuyNowClick() }
    }

    private fun handleCartButtonClick() {
        val newItem = ItemsModel(
            id = "${item.id}_${System.currentTimeMillis()}", // Unique ID for each added product
            title = item.title + " ($selectedSize)", // Append selected size
            description = item.description,
            picUrl = item.picUrl,
            price = calculatePriceForSize(), // Dynamically calculate price
            extra = item.extra,
            categoryId = item.categoryId,
            quantity = 1,
            size = selectedSize
        )

        // Add the item to the cart
        CartManager.addToCart(newItem)
        Log.d("CartDebug", "${newItem.title} added to cart with size: $selectedSize")
        Toast.makeText(this, "${newItem.title} added to cart!", Toast.LENGTH_SHORT).show()

        // Navigate to AddToCartActivity
        startActivity(Intent(this, AddToCartActivity::class.java))
    }

    private fun handleBuyNowClick() {
        val price = calculatePriceForSize()

        if (price <= 0) {
            Toast.makeText(this, "Error: Invalid total amount!", Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent(this, CheckoutActivity::class.java).apply {
            putExtra("TOTAL_AMOUNT", price) // ✅ Pass correct price to checkout
            putExtra("PRODUCT_NAME", item.title)
            putExtra("PRODUCT_SIZE", selectedSize)
        }
        startActivity(intent)
    }

    private fun bundle() {
        binding.apply {
            item = intent.getParcelableExtra("object", ItemsModel::class.java)!!
            titleText.text = item.title
            descriptionText.text = item.description
            subtitleText.text = item.extra
            priceText.text = "$" + calculatePriceForSize()  // Show updated price

            Glide.with(context)
                .load(item.picUrl[0])
                .apply(RequestOptions.bitmapTransform(com.bumptech.glide.load.resource.bitmap.RoundedCorners(100)))
                .into(binding.imgDetail)

            backBtn.setOnClickListener { finish() }

            sizeBtn1.setOnClickListener {
                selectedSize = "S"
                updateSizeSelection()
            }

            sizeBtn2.setOnClickListener {
                selectedSize = "M"
                updateSizeSelection()
            }

            sizeBtn3.setOnClickListener {
                selectedSize = "L"
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

            // Update displayed price based on selection
            priceText.text = "$" + calculatePriceForSize()
        }
    }

    private fun calculatePriceForSize(): Double {
        return when (selectedSize) {
            "S" -> item.price
            "M" -> item.price * 1.5  // Medium size increases price by 50%
            "L" -> item.price * 2    // Large size is double the base price
            else -> item.price
        }
    }
}
