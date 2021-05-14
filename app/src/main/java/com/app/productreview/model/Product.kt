package com.app.productreview.model

import java.io.Serializable

data class Product(
    val currency: String,
    val price: Int,
    val id: String,
    val name: String,
    val description: String
) : Serializable