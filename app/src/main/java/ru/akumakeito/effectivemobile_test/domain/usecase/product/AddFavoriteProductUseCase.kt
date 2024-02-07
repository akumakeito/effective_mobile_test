package ru.akumakeito.effectivemobile_test.domain.usecase.product

import ru.akumakeito.effectivemobile_test.domain.model.Product
import ru.akumakeito.effectivemobile_test.domain.repository.ProductRepository
import javax.inject.Inject

class AddFavoriteProductUseCase @Inject constructor(
    private val repository: ProductRepository,
) {
    suspend fun execute(product: Product) = repository.addFavoriteProduct(product)
}