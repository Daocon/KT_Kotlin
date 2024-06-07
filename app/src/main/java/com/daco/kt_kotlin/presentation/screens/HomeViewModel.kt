package com.daco.kt_kotlin.presentation.screens

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.daco.kt_kotlin.model.Product

class HomeViewModel: ViewModel() {
    val products = mutableStateListOf<Product>(
        Product(1, "Product 1", 100.0, "Description 1"),
        Product(2, "Product 2", 200.0, "Description 2"),
        Product(3, "Product 3", 300.0, "Description 3"),
        Product(4, "Product 4", 400.0, "Description 4"),
        Product(5, "Product 5", 500.0, "Description 5")
    )

    fun addProduct(product: Product) {
        products.add(product)
    }

    fun updateProduct(product: Product) {
        val index = products.indexOfFirst { it.id == product.id }
        if (index != -1) {
            products[index] = product
        }
    }
}