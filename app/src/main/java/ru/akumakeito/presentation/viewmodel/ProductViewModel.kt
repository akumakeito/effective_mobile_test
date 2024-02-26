package ru.akumakeito.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.akumakeito.effectivemobile_test.domain.model.Price
import ru.akumakeito.effectivemobile_test.domain.model.Product
import ru.akumakeito.effectivemobile_test.domain.model.Tags
import ru.akumakeito.effectivemobile_test.domain.repository.ProductRepository
import ru.akumakeito.effectivemobile_test.domain.usecase.product.AddFavoriteProductUseCase
import ru.akumakeito.effectivemobile_test.domain.usecase.product.GetProductByIdUseCase
import ru.akumakeito.effectivemobile_test.domain.usecase.product.GetProductsUseCase
import ru.akumakeito.effectivemobile_test.domain.usecase.product.SortByUseCase
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
    private val sortByUseCase: SortByUseCase,
    private val repository: ProductRepository,
    private val addFavoriteProductUseCase: AddFavoriteProductUseCase
) : ViewModel() {

    init {
        getTags()
        getProducts()
    }

    private var _tags = listOf<String>("")
    val tags = _tags

    private val _products = repository.dataProduct.asLiveData()
    val products = _products

    private val _favoriteProducts = repository.favoriteProducts.asLiveData()
    val favoriteProducts = _favoriteProducts

    private val _product = MutableLiveData(emptyProduct)
    val product = _product

    fun getProducts() {
        viewModelScope.launch {
            getProductsUseCase()
        }

    }

    private fun getTags() {

        _tags = Tags.values().map { it.tagName }

        Log.d("listTags", _tags.toString())
    }

    fun getProductsByTag(tagName: String) {
        viewModelScope.launch {
//            _products.value = getProductsByTagUseCase.execute(tagName)
        }
    }

    fun updateFavoriteProduct(product: Product) {
        viewModelScope.launch {
            addFavoriteProductUseCase.execute(product)
        }
    }

    fun sortBy(sortParam: String) {
        viewModelScope.launch {
            Log.d("sorting", "vm ${sortParam}")
            sortByUseCase.invoke(sortParam)
        }

    }

    fun getProductById(id: String) {
        viewModelScope.launch {
            _product.value = getProductByIdUseCase.invoke(id)
        }
    }

}