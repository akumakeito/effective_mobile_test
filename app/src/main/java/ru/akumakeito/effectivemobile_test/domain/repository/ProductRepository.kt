package ru.akumakeito.effectivemobile_test.domain.repository

import ru.akumakeito.effectivemobile_test.domain.model.Product

interface ProductRepository  {

    suspend fun getProducts() : List<Product>

    suspend fun addImageListToProduct(jsonString: String)

    suspend fun addImageListToProduct()

}