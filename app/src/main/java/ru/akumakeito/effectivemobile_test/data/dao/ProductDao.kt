package ru.akumakeito.effectivemobile_test.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
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

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getProductById(id: String) : ProductEntity

    @Query("SELECT * FROM products WHERE isFavorite = :isFavorite")
    fun getFavoriteProductsById(isFavorite: Boolean) : Flow<List<ProductEntity>>

    @Query("UPDATE products SET imageList = :imageList WHERE id = :id")
    suspend fun updateImageListById(imageList: List<Int>, id: String)

    @Query("""
        UPDATE products SET 
        isFavorite = CASE WHEN isFavorite THEN 0 ELSE 1 END WHERE id = :id
    """)
    suspend fun updateIsFavorite( id: String)


    @Query("SELECT * FROM products")
    fun getAllProducts() : Flow<List<ProductEntity>>

}