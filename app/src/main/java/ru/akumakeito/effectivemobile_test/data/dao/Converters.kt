package ru.akumakeito.effectivemobile_test.data.dao

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.akumakeito.effectivemobile_test.domain.model.ProductInfo
import ru.akumakeito.effectivemobile_test.domain.model.Tags
import javax.inject.Inject

@ProvidedTypeConverter
class Converters @Inject constructor(val gson : Gson) {

    @TypeConverter
    fun fromTagsList(tags : List<Tags>) :String {
        val type = object : TypeToken<List<Tags>>() {}.type
        return gson.toJson(tags, type)
    }

    @TypeConverter
    fun toTagsList(tagsString : String) : List<Tags> {
        val listType = object : TypeToken<List<Tags>>() {}.type
        return gson.fromJson(tagsString, listType)
    }



    @TypeConverter
    fun fromProductInfoList(productInfo : List<ProductInfo>) = gson.toJson(productInfo)

    @TypeConverter
    fun toProductInfoList(productInfoListString : String) : List<ProductInfo> {
        val listType = object : TypeToken<List<ProductInfo>>() {}.type
        return gson.fromJson(productInfoListString, listType)
    }

    @TypeConverter
    fun fromImageList(imageList : List<Int>) = gson.toJson(imageList)

    @TypeConverter
    fun toImageList(imageListString : String) : List<Int> {
        val listType = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(imageListString, listType)
    }


}