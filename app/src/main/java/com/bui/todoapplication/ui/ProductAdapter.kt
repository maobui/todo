package com.bui.todoapplication.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bui.todoapplication.R
import com.bui.todoapplication.databinding.ProductItemBinding
import com.bui.todoapplication.model.Product

class ProductAdapter :
    ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ProductItemBinding.bind(itemView)

        fun bind(product: Product) {
            binding.textViewName.text = product.name
            binding.textViewPrice.text = product.price.toString()
            binding.textViewQuality.text = product.quantity.toString()
        }

        companion object {
            fun create(parent: ViewGroup): ProductViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.product_item, parent, false)
                return ProductViewHolder(view)
            }
        }
    }

    class ProductsComparator : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }
    }
}