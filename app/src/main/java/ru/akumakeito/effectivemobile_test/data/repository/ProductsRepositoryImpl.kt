package ru.akumakeito.effectivemobile_test.data.repository

import android.content.Context
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.akumakeito.effectivemobile_test.R
import ru.akumakeito.effectivemobile_test.data.dao.ProductDao
import ru.akumakeito.effectivemobile_test.data.model.toEntity
import ru.akumakeito.effectivemobile_test.data.model.toProduct
import ru.akumakeito.effectivemobile_test.domain.model.Product
import ru.akumakeito.effectivemobile_test.domain.repository.ProductRepository
import ru.akumakeito.network.ProductApiService
import ru.akumakeito.util.ImageDeserializer
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val dao: ProductDao,
    private val api : ProductApiService,
    private val gson: Gson,
    private val imageDeserializer: ImageDeserializer,
    @ApplicationContext private val context: Context
) : ProductRepository {

    override suspend fun addImageListToProduct() {
        val res = context.resources
        val inputStream = res.openRawResource(R.raw.itemimages)
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        addImageListToProduct(jsonString)
    }

    override suspend fun getProducts(): List<Product> {
        if (dao.isEmpty()) {
            try {
                val result = api.getAllProducts()
                dao.insertAllProducts(result.toEntity())
                addImageListToProduct()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return dao.getAllProducts().toProduct()
    }

    override suspend fun addImageListToProduct(jsonString: String) {
        val flagList = imageDeserializer.deserialize(jsonString)
        val res = context.resources
        flagList.forEach { imageJson ->
            val imageId1 = res.getIdentifier(imageJson.imageList[0], "drawable", context.packageName)
            val imageId2 = res.getIdentifier(imageJson.imageList[1], "drawable", context.packageName)

            dao.updateImageListById(listOf(imageId1,imageId2), imageJson.id)
        }
    }
}