package ru.akumakeito.effectivemobile_test.domain.usecase.product

import ru.akumakeito.effectivemobile_test.domain.repository.ProductRepository
import javax.inject.Inject

class SortByUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(sortParameter: String) = repository.sortBy(sortParameter)
}