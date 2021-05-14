package com.app.productreview.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.productreview.adapter.ProductAdapter
import com.app.productreview.R
import com.app.productreview.Utils
import com.app.productreview.databinding.ProductOverviewBinding
import com.app.productreview.model.Product
import com.app.productreview.network.ApiHelper
import com.app.productreview.network.RetrofitBuilder
import com.app.productreview.resource.Status
import com.app.productreview.viewmodel.ProductViewModel
import com.app.productreview.viewmodel.ProductViewModelFactory

class MainActivity : AppCompatActivity(), ProductAdapter.OnClickListener {

    lateinit var viewModel: ProductViewModel

    private var searchProductsList: ArrayList<Product> = arrayListOf()

    private var productsList: ArrayList<Product> = arrayListOf()

    private lateinit var binding: ProductOverviewBinding

    private var productAdapter: ProductAdapter =
        ProductAdapter(productsList)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.product_overview)

        if (!Utils.isNetworkConnected(this)) {
            AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(getString(R.string.internetConnectioinLabel))
                .setMessage(getString(R.string.internetConnectionText))
                .setPositiveButton(
                    "Ok"
                ) { _, _ -> finish() }.show()
        }

        viewModel = ViewModelProvider(this, ProductViewModelFactory(ApiHelper(RetrofitBuilder.apiServiceProduct)))
            .get(ProductViewModel::class.java)

        productAdapter.setOnClickListener(this)
        binding.adapter = productAdapter
        binding.viewModel = viewModel
        binding.search.isActivated = true
        binding.search.queryHint = getString(R.string.search)
        binding.search.onActionViewExpanded()
        binding.search.isIconified
        binding.search.clearFocus()

        setupObservers()

        searchByNameOrDescription()
    }

    private fun setupObservers() {
        viewModel.getProductDetails().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.productRecyclerView.visibility = View.VISIBLE
                        resource.data?.let { products -> retrieveList(products) }
                    }
                    Status.ERROR -> {
                        binding.productRecyclerView.visibility = View.VISIBLE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        binding.productRecyclerView.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun retrieveList(products: List<Product>) {
        productAdapter.apply {
            addUsers(products)
            notifyDataSetChanged()
        }
    }

    override fun onClick(clickedItem: Product) {
        val intent = Intent(this, ProductDetailsActivity::class.java)
        intent.putExtra("product", clickedItem)
        startActivity(intent)
    }

    private fun searchByNameOrDescription() {
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                search(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                search(newText)
                return true
            }
        })
    }

    private fun search(text: String?) {
        searchProductsList = arrayListOf()

        text?.let {
            productsList.forEach { product ->
                if (product.name.contains(text, true) ||
                    product.description.contains(text, true) ||
                    product.price.toString().contains(text, true)
                ) {
                    searchProductsList.add(product)
                    updateRecyclerView()
                }
            }
            if (searchProductsList.isEmpty()) {
                Toast.makeText(this, "No match found!", Toast.LENGTH_SHORT).show()
            }
            updateRecyclerView()
        }
    }

    private fun updateRecyclerView() {
        binding.productRecyclerView.apply {
            productAdapter.products = searchProductsList
            productAdapter.notifyDataSetChanged()
        }
    }
}
