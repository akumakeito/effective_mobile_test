package ru.akumakeito.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.akumakeito.effectivemobile_test.domain.model.FilterData
import ru.akumakeito.effectivemobile_test.domain.model.Price
import ru.akumakeito.effectivemobile_test.domain.model.Product
import ru.akumakeito.effectivemobile_test.domain.model.SortType
import ru.akumakeito.effectivemobile_test.domain.model.Tags
import ru.akumakeito.effectivemobile_test.domain.model.UiState
import ru.akumakeito.effectivemobile_test.domain.repository.ProductRepository
import ru.akumakeito.effectivemobile_test.domain.usecase.product.GetProductByIdUseCase
import ru.akumakeito.effectivemobile_test.domain.usecase.product.GetProductsUseCase
import ru.akumakeito.effectivemobile_test.domain.usecase.product.UpdateFavoriteProductUseCase
import ru.akumakeito.util.Constants.Companion.KEY_FILTER_DATA
import javax.inject.Inject

val emptyProduct = Product(
    id = "",
    title = "",
    subtitle = "",
    description = "",
    price = Price(0, 0, 0, ""),
    feedback = null,
    tags = emptyList(),
    available = 0,
    info = emptyList(),
    ingredients = "",
    imageList = emptyList()
)

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val repository: ProductRepository,
    private val updateFavoriteProductUseCase: UpdateFavoriteProductUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    init {
        getTags()
        getProducts()
    }

    private var _tags = listOf<String>("")
    val tags = _tags

    private val _products = repository.dataProduct
    val products = _products

    private val _favoriteProducts = repository.favoriteProducts.asLiveData()
    val favoriteProducts = _favoriteProducts

    private val _product = MutableLiveData(emptyProduct)
    val product = _product

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    val sortingList = mutableListOf<String>()

    init {

        savedStateHandle.get<FilterData>(KEY_FILTER_DATA)?.let { filterData ->
            _uiState.update {
                it.copy(sortType = filterData.sortType, loading = true)
            }
        }

    }


    fun setSortType(sortType: SortType) {
        _uiState.update {
            it.copy(sortType = sortType)
        }
    }


    fun resetFilters() {
        _uiState.update {
            it.copy(clearAllFilters = true, sortType = SortType.NONE)
        }
    }

    fun applyFilters() {
        _uiState.update {
            it.copy(applyAllFilters = true)
        }
    }

    fun getProducts() {
        viewModelScope.launch {
            getProductsUseCase()
            _uiState.update {
                it.copy(loading = false)
            }
        }

    }

    private fun getTags() {

        _tags = Tags.values().map { it.tagName }

        Log.d("listTags", _tags.toString())
    }


    fun updateFavoriteProduct(product: Product) {
        viewModelScope.launch {
            updateFavoriteProductUseCase.execute(product)
        }
    }

    fun sortBy(sortParam: String) {
        viewModelScope.launch {
            Log.d("sorting", "vm ${sortParam}")
        }

    }

    fun getProductById(id: String) {
        viewModelScope.launch {
            _product.value = getProductByIdUseCase.invoke(id)
        }
    }

}