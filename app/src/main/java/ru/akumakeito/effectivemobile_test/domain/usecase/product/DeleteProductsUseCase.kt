package ru.akumakeito.effectivemobile_test.domain.usecase.product

import ru.akumakeito.effectivemobile_test.domain.repository.ProductRepository
import javax.inject.Inject

class DeleteProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke() = repository.deleteAll()
}