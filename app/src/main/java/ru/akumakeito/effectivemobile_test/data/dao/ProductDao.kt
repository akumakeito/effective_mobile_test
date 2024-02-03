package ru.akumakeito.effectivemobile_test.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.akumakeito.effectivemobile_test.data.model.ProductEntity

@Dao
interface ProductDao {

    @Query("SELECT COUNT(*)==0 FROM products")
    suspend fun isEmpty() : Boolean
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProducts(products : List<ProductEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(products : ProductEntity)

    @Query("DELETE FROM products")
    suspend fun deleteAll()

    @Query("UPDATE products SET imageList = :imageList WHERE id = :id")
    suspend fun updateImageListById(imageList: List<Int>, id: String)

    @Query("SELECT * FROM products")
    suspend fun getAllProducts() : List<ProductEntity>
}