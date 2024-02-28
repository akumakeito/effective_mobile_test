package ru.akumakeito.effectivemobile_test.domain.model

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.akumakeito.effectivemobile_test.R

@ApplicationContext lateinit var context : Context
enum class SortType(val value: String) {
    POPULATION_ASC(value = context.getString(R.string.by_popular)),
    ALPHABETICAL_ASC(value = context.getString(R.string.by_price_increase)),
    ALPHABETICAL_DESC(value = context.getString(R.string.by_price_decrease))

}

    data class UiState(
        val loading : Boolean = false,
        val sortType: SortType = SortType.POPULATION_ASC,
        val content : List<Product> = emptyList(),
        val clearAllFilters: Boolean = false,
        val applyAllFilters: Boolean = false
    ) {

        val chipAlphabeticalSorAsctIsChecked = sortType == SortType.ALPHABETICAL_ASC
        val chipAlphabeticalSortDescIsChecked = sortType == SortType.ALPHABETICAL_DESC
        val chipByPopulationSortIsChecked = sortType == SortType.POPULATION_ASC
    }

data class FilterData(
    val sortType: SortType = SortType.POPULATION_ASC
) : java.io.Serializable
