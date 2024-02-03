package ru.akumakeito.network

import retrofit2.http.GET
import ru.akumakeito.effectivemobile_test.data.dto.ProductDTO

interface ProductApiService {

    @GET("v3/97e721a7-0a66-4cae-b445-83cc0bcf9010")
    fun getAllProducts() : List<ProductDTO>

}