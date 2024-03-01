package ru.akumakeito.effectivemobile_test.domain.model

import ru.akumakeito.effectivemobile_test.R
import ru.akumakeito.presentation.ui.fragments.FragmentCatalog.Companion.context


enum class SortType(val valueProvider: () -> String) {
    POPULARITY_ASC(valueProvider = {context.getString(R.string.by_popular)}),
    PRICE_ASC(valueProvider = { context.getString(R.string.by_price_increase) }),
    PRICE_DESC(valueProvider = { context.getString(R.string.by_price_decrease) });

    val value : String by lazy { valueProvider.invoke()}

}

    data class UiState(
        val loading : Boolean = false,
        val sortType: SortType = SortType.POPULARITY_ASC,
        val content : List<Product> = emptyList(),
        val clearAllFilters: Boolean = false,
        val applyAllFilters: Boolean = false
    ) {

        val chipAlphabeticalSorAscIsChecked = sortType == SortType.PRICE_ASC
        val chipAlphabeticalSortDescIsChecked = sortType == SortType.PRICE_DESC
        val chipByPopulationSortIsChecked = sortType == SortType.POPULARITY_ASC
    }

data class FilterData(
    val sortType: SortType = SortType.POPULARITY_ASC
) : java.io.Serializable
