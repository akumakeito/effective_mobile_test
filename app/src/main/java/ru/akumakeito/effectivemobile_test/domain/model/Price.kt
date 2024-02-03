package ru.akumakeito.effectivemobile_test.domain.model

data class Price(
    val price: Int,
    val discount: Int,
    val priceWithDiscount: Int,
    val unit : Char
)
