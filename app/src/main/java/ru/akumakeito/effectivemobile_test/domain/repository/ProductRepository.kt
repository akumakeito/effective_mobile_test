package ru.akumakeito.effectivemobile_test.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.akumakeito.effectivemobile_test.domain.model.Product

interface ProductRepository  {

    val dataProduct : Flow<List<Product>>
    val favoriteProducts : Flow<List<Product>>
    suspend fun getProducts()

    suspend fun getTags() : List<String>

    suspend fun addImageListToProduct(jsonString: String)

    suspend fun addImageListToProduct()

    suspend fun addFavoriteProduct(product: Product)

    suspend fun getProductsById(id: String) : Product

    suspend fun getProductsByTag(tagName: String): List<Product>
    suspend fun sortBy(sortParameter : String)

    suspend fun deleteAll()


}