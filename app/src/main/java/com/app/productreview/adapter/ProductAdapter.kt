package com.app.productreview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.productreview.databinding.ProductItemBinding
import com.app.productreview.model.Product
import kotlin.collections.ArrayList

class ProductAdapter(var products: ArrayList<Product>) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    var itemClickListener: OnClickListener? = null

    interface OnClickListener {
        fun onClick(clickedItem: Product)
    }

    fun setOnClickListener(callback: OnClickListener) {
        itemClickListener = callback
    }

    class ViewHolder(val binding : ProductItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ProductItemBinding.inflate(LayoutInflater.from(parent.context))
        )

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.product = products[position]

        holder.binding.root.setOnClickListener {
            itemClickListener?.onClick(products[position])
        }
    }

    fun addUsers(products: List<Product>) {
        this.products.apply {
            clear()
            addAll(products)
        }
    }

}