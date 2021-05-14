package com.app.productreview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.productreview.databinding.ReviewItemBinding
import com.app.productreview.model.Review

class ReviewAdapter(private val reviews: ArrayList<Review>) :
    RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {

    class ViewHolder(val binding: ReviewItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ReviewItemBinding.inflate(LayoutInflater.from(parent.context)))

    override fun getItemCount(): Int {
        return reviews.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.review = reviews[position]
        holder.binding.rating.text = reviews[position].rating.toString()
    }

    fun addReviews(reviews: List<Review>) {
        this.reviews.apply {
            clear()
            addAll(reviews)
        }

    }
}