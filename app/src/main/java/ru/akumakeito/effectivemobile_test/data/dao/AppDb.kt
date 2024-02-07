package ru.akumakeito.effectivemobile_test.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.akumakeito.effectivemobile_test.data.model.ProductEntity

@Database(entities = [ProductEntity::class], version = 5, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDb : RoomDatabase() {

    abstract fun productDao(): ProductDao

}