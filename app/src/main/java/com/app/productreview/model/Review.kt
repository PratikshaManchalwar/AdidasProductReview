package com.app.productreview.model

import java.io.Serializable

data class Review (
    val productId: String,
    val locale: String,
    val rating: Int,
    val text: String
) : Serializable