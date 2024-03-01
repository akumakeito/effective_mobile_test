package ru.akumakeito.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
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

    private val _sortedProducts = MutableStateFlow<List<Product>>(emptyList())
    val sortedProducts = _sortedProducts as StateFlow<List<Product>>

    private val _favoriteProducts = repository.favoriteProducts.asLiveData()
    val favoriteProducts = _favoriteProducts

    private val _product = MutableLiveData(emptyProduct)
    val product = _product

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()


    fun setSortType(sortType: SortType) {
        _uiState.update {
            it.copy(sortType = sortType)
        }
    }


    fun resetFilters() {
        _uiState.update {
            it.copy(clearAllFilters = true, sortType = SortType.POPULARITY_ASC)
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

    fun sortBy(sortParam: SortType) {
        viewModelScope.launch {

            _products.map {

                when (sortParam) {
                    SortType.POPULARITY_ASC -> it.sortedByDescending { it.feedback?.rating }
                    SortType.PRICE_ASC -> it.sortedBy { it.price.priceWithDiscount }
                    SortType.PRICE_DESC -> it.sortedByDescending { it.price.priceWithDiscount }
                }

            }.collect{
                _sortedProducts.value = it
                Log.d("sorting", "vm ${sortParam}")

                Log.d("sorting", "sorted list ${it}")
            }


        }

    }

    fun getProductById(id: String) {
        viewModelScope.launch {
            _product.value = getProductByIdUseCase.invoke(id)
        }
    }

}