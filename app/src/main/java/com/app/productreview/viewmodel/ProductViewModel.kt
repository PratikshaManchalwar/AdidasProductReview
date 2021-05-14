package com.app.productreview.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.app.productreview.resource.Resource
import kotlinx.coroutines.Dispatchers

class ProductViewModel constructor(private val repository: ProductRepository)  : ViewModel() {

    fun getProductDetails() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getProductDetails()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}