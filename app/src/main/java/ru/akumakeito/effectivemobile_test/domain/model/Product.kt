package ru.akumakeito.effectivemobile_test.domain.model

import androidx.annotation.DrawableRes

data class Product(
    val id: String,
    val title : String,
    val subtitle :String,
    val price : Price,
    val feedback : Feedback,
    val tags : List<Tags>,
    val available : Int,
    val description : String,
    val info : List<ProductInfo>,
    val ingredients : String,
    val imageList :  List<Int> = emptyList(),
    val isFavorite : Boolean = false
)
