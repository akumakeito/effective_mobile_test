package ru.akumakeito.effectivemobile_test.domain.usecase.product

import android.util.Log
import ru.akumakeito.effectivemobile_test.domain.repository.ProductRepository
import javax.inject.Inject

class SortByUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(sortParameter: String) {
        Log.d("sorting", "usecase ${sortParameter}")
        repository.sortBy(sortParameter)
    }
}