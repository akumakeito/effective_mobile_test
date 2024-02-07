package ru.akumakeito.effectivemobile_test.data.repository

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.akumakeito.effectivemobile_test.R
import ru.akumakeito.effectivemobile_test.data.dao.ProductDao
import ru.akumakeito.effectivemobile_test.data.model.ProductEntity
import ru.akumakeito.effectivemobile_test.data.model.toEntity
import ru.akumakeito.effectivemobile_test.data.model.toProduct
import ru.akumakeito.effectivemobile_test.domain.model.Product
import ru.akumakeito.effectivemobile_test.domain.model.Tags
import ru.akumakeito.effectivemobile_test.domain.repository.ProductRepository
import ru.akumakeito.network.ProductApiService
import ru.akumakeito.util.ImageDeserializer
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val dao: ProductDao,
    private val api: ProductApiService,
    private val gson: Gson,
    private val imageDeserializer: ImageDeserializer,
    @ApplicationContext private val context: Context,
) : ProductRepository {

    override val favoriteProducts: Flow<List<Product>> = dao.getFavoriteProductsById(true)
        .map(List<ProductEntity>::toProduct)
        .flowOn(Dispatchers.IO)

    override val dataProduct: Flow<List<Product>> = dao.getAllProducts()
        .map(List<ProductEntity>::toProduct)
        .flowOn(Dispatchers.IO)

    override suspend fun addImageListToProduct() {
        val res = context.resources
        val inputStream = res.openRawResource(R.raw.itemimages)
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        addImageListToProduct(jsonString)
    }

    override suspend fun addFavoriteProduct(product: Product) {
        dao.updateIsFavorite(product.id)
    }

    override suspend fun getProductsById(id: String): Product = dao.getProductById(id).toProduct()

    override suspend fun getProducts() {
        if (dao.isEmpty()) {
            try {
                val result = api.getAllProducts()
                dao.insertAllProducts(result.items.toEntity())
                addImageListToProduct()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }



    override suspend fun addImageListToProduct(jsonString: String) {
        val flagList = imageDeserializer.deserialize(jsonString)
        val res = context.resources
        flagList.forEach { imageJson ->
            val imageId1 =
                res.getIdentifier(imageJson.imageList[0], "drawable", context.packageName)
            val imageId2 =
                res.getIdentifier(imageJson.imageList[1], "drawable", context.packageName)

            dao.updateImageListById(listOf(imageId1, imageId2), imageJson.id)
        }
    }

    override suspend fun getTags(): List<String> {
        val listTags = mutableListOf<String>()
        Tags.entries.forEach {
            listTags.add(it.tagName)
        }

        return listTags.toList()
    }

    override suspend fun getProductsByTag(tagName: String): List<Product> {
        val tag = Tags.entries.find { it.tagName == tagName }
        val productList = dataProduct
        val filteredProductList = mutableListOf<Product>()
        productList.collect { products ->
            products.forEach { product ->
                product.tags.forEach {
                    if (it == tag) {
                        filteredProductList.add(product)
                    }
                }

            }


        }
        return filteredProductList.toList()
    }

    override suspend fun sortBy(sortParameter: String) {
        dataProduct.map { list ->
            list.sortedByDescending {
                it.feedback?.rating ?: 0f
                Log.d("sorting", "by popular ${list}")
            }
        }.flowOn(Dispatchers.IO)


//        data.collect { products ->
//            when (sortParameter) {
//                context.getString(R.string.by_popular) -> {
//
//                    products.sortedBy { product ->
//                        product.feedback?.rating
//                    }
//                    Log.d("sorting", "by popular ${products}")
//                }
//
//                context.getString(R.string.by_price_decrease) -> {
//
//
//                    products.sortedBy { product ->
//                        product.price.priceWithDiscount
//                    }.reversed()
//                    Log.d("sorting", "by decrese ${products}")
//                }
//
//                context.getString(R.string.by_price_increase) -> {
//                    products.sortedBy { product ->
//                        product.price.priceWithDiscount
//                    }
//                    Log.d("sorting", "by increse ${products}")
//
//                }
//            }
//
//        }
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }
}