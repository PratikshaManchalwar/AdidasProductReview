package com.app.productreview.view

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.productreview.R
import com.app.productreview.Utils
import com.app.productreview.adapter.ReviewAdapter
import com.app.productreview.databinding.ProductDetailsBinding
import com.app.productreview.model.Product
import com.app.productreview.model.Review
import com.app.productreview.network.ApiHelper
import com.app.productreview.network.RetrofitBuilder
import com.app.productreview.resource.Status
import com.app.productreview.viewmodel.ReviewViewModel
import com.app.productreview.viewmodel.ReviewViewModelFactory

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: ReviewViewModel

    private var reviewList: ArrayList<Review> = arrayListOf()

    private var reviewAdapter: ReviewAdapter = ReviewAdapter(reviewList)

    private lateinit var product: Product

    private lateinit var binding: ProductDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.product_details)

        product = intent.getSerializableExtra("product") as Product

        binding.backIcon.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }

        if (!Utils.isNetworkConnected(this)) {
            AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(getString(R.string.internetConnectioinLabel))
                .setMessage(getString(R.string.internetConnectionText))
                .setPositiveButton(
                    "Ok"
                ) { _, _ -> finish() }.show()
        }

        binding.productName.text = product.name
        binding.productDescription.text = product.description
        binding.productPrice.text = product.price.toString() + product.currency

        viewModel = ViewModelProvider(
            this, ReviewViewModelFactory(
                ApiHelper(RetrofitBuilder.apiServiceReview)
            )
        ).get(
            ReviewViewModel::class.java
        )

        binding.adapter = reviewAdapter

        getReview()

        binding.addReviewButton.setOnClickListener {
            showReviewDialog()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun getReview() {
        viewModel.getReviews(product.id).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {

                    Status.SUCCESS -> {
                        binding.reviewRecyclerview.visibility = View.VISIBLE
                        resource.data?.let { reviews -> retrieveReviews(reviews) }
                    }
                    Status.ERROR -> {
                        binding.reviewRecyclerview.visibility = View.VISIBLE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        binding.reviewRecyclerview.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun retrieveReviews(reviews: List<Review>) {
        reviewAdapter.apply {
            addReviews(reviews)
            notifyDataSetChanged()
        }
    }

    private fun showReviewDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.review_dialog)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window!!.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.gravity = Gravity.CENTER

        dialog.window!!.attributes = layoutParams

        val reviewMessage = dialog.findViewById(R.id.reviewMessage) as EditText
        val ratingsSpinner = dialog.findViewById<Spinner>(R.id.ratingsSpinner)
        val submitReview = dialog.findViewById(R.id.submitReview) as Button
        val closeIcon = dialog.findViewById(R.id.closeIcon) as ImageView

        val ratingsAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.Ratings,
            android.R.layout.simple_selectable_list_item
        )
        ratingsSpinner.adapter = ratingsAdapter

        closeIcon.setOnClickListener {
            dialog.dismiss()
        }

        submitReview.setOnClickListener {
            val review = Review(
                product.id, "en-IN,en;q=0.9", getRating(ratingsSpinner.selectedItem.toString()),
                reviewMessage.text.toString()
            )

            viewModel.addReviews(product.id, review).observe(this, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            binding.reviewRecyclerview.visibility = View.VISIBLE
                            resource.data?.let { getReview() }
                        }
                        Status.ERROR -> {
                            binding.reviewRecyclerview.visibility = View.VISIBLE
                            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                        }
                        Status.LOADING -> {
                            binding.reviewRecyclerview.visibility = View.GONE
                        }
                    }
                }
            })

            dialog.dismiss()
        }
        dialog.show()
    }

    fun getRating(ratingText: String): Int {
        when (ratingText) {
            "Very Satisfied" -> return 5
            "Satisfied" -> return 4
            "Fair" -> return 3
            "Dissatisfied" -> return 2
            "Very Dissatisfied" -> return 1
        }
        return 0
    }
}