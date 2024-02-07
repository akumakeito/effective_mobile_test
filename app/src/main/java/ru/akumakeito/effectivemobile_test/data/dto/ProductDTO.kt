package ru.akumakeito.effectivemobile_test.data.dto

import ru.akumakeito.effectivemobile_test.domain.model.Feedback
import ru.akumakeito.effectivemobile_test.domain.model.Price
import ru.akumakeito.effectivemobile_test.domain.model.ProductInfo
import ru.akumakeito.effectivemobile_test.domain.model.Tags

data class ProductDTO(
    val id: String,
    val title: String,
    val subtitle: String,
    val price: Price,
    val feedback: Feedback?,
    val tags: List<Tags>,
    val available: Int,
    val description: String,
    val info: List<ProductInfo>,
    val ingredients: String
)
