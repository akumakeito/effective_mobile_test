package ru.akumakeito.effectivemobile_test.data.dto

import com.google.gson.annotations.SerializedName

data class ImageItemJson(
    @SerializedName("id")
    val id: String,
    @SerializedName("image_list")
    val imageList: List<String>
)