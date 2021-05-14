package com.app.productreview.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.productreview.network.ApiHelper

class ProductViewModelFactory constructor(private val apiHelper: ApiHelper): ViewModelProvider.Factory {

   override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
           return ProductViewModel(ProductRepository(apiHelper)) as T
       }
       throw IllegalArgumentException("Unknown class name")
   }

}