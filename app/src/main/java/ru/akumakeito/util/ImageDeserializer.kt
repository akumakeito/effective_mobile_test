package ru.akumakeito.util

import com.google.gson.Gson
import ru.akumakeito.effectivemobile_test.data.dto.ImageItemJson
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageDeserializer  @Inject constructor(
    private val gson: Gson
) {
    fun deserialize(jsonString: String): List<ImageItemJson> {
        val flagType = object : com.google.gson.reflect.TypeToken<List<ImageItemJson>>() {}.type
        return gson.fromJson(jsonString, flagType)
    }
}