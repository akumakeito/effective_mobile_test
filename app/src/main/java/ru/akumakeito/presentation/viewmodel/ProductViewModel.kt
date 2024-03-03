package ru.akumakeito.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.akumakeito.effectivemobile_test.domain.model.Price
import ru.akumakeito.effectivemobile_test.domain.model.Product
import ru.akumakeito.effectivemobile_test.domain.model.SortType
import ru.akumakeito.effectivemobile_test.domain.model.Tags
import ru.akumakeito.effectivemobile_test.domain.model.UiState
import ru.akumakeito.effectivemobile_test.domain.repository.ProductRepository
import ru.akumakeito.effectivemobile_test.domain.usecase.product.GetProductByIdUseCase
import ru.akumakeito.effectivemobile_test.domain.usecase.product.GetProductsUseCase
import ru.akumakeito.effectivemobile_test.domain.usecase.product.UpdateFavoriteProductUseCase
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
) : ViewModel() {

    init {
        getTags()
        getProducts()
    }

    private fun getTags() {
        _tags = Tags.entries.map { it.tagName }
    }

    private lateinit var _tags: List<String>
    val tags = _tags

    private val _uiState = MutableStateFlow(UiState())

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _products = _uiState.flatMapLatest { filter ->
        repository.dataProduct.map { productList ->
            applyFilters(filter.filterTag, productList)
        }.map { productList ->
            sortBy(filter.sortType, productList)
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val products = _products

    private val _favoriteProducts = repository.favoriteProducts.asLiveData()
    val favoriteProducts = _favoriteProducts

    private val _product = MutableLiveData(emptyProduct)
    val product = _product


    fun setSortType(sortType: SortType) {
        _uiState.update {
            it.copy(sortType = sortType)
        }
    }

    fun setFilter(filterTag: Tags) {
        _uiState.update {
            it.copy(filterTag = filterTag)
        }
    }


    fun resetFilters() {
        _uiState.update {
            it.copy(filterTag = Tags.notag)
        }
    }

    private fun applyFilters(tag: Tags, list: List<Product>) = if (tag == Tags.notag) {
        list
    } else {
        list.filter { product ->
            product.tags.contains(tag)
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

    fun updateFavoriteProduct(product: Product) {
        viewModelScope.launch {
            updateFavoriteProductUseCase.execute(product)
        }
    }

    fun sortBy(sortParam: SortType, list: List<Product>) = when (sortParam) {
        SortType.POPULARITY_ASC -> list.sortedByDescending { it.feedback?.rating }
        SortType.PRICE_ASC -> list.sortedBy { it.price.priceWithDiscount }
        SortType.PRICE_DESC -> list.sortedByDescending { it.price.priceWithDiscount }
    }


    fun getProductById(id: String) {
        viewModelScope.launch {
            _product.value = getProductByIdUseCase.invoke(id)
        }
    }

}