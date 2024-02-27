package ru.akumakeito.effectivemobile_test.domain.model

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.akumakeito.effectivemobile_test.R

@ApplicationContext lateinit var context : Context
enum class SortType(sortString: String) {
    NONE(sortString = context.getString(R.string.by_popular)),
    ALPHABETICAL_ASC(sortString = context.getString(R.string.by_price_increase)),
    ALPHABETICAL_DESC(sortString = context.getString(R.string.by_price_decrease)),
    POPULATION_ASC(sortString = context.getString(R.string.by_popular))
}

    data class UiState(
        val loading : Boolean = false,
        val sortType: SortType = SortType.NONE,
        val content : List<Product> = emptyList(),
        val clearAllFilters: Boolean = false,
        val applyAllFilters: Boolean = false
    ) {

        val chipAlphabeticalSorAsctIsChecked = sortType == SortType.ALPHABETICAL_ASC
        val chipAlphabeticalSortDescIsChecked = sortType == SortType.ALPHABETICAL_DESC
        val chipByPopulationSortIsChecked = sortType == SortType.POPULATION_ASC
    }

data class FilterData(
    val sortType: SortType = SortType.NONE
) : java.io.Serializable
