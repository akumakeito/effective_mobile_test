package ru.akumakeito.network

import retrofit2.http.GET
import ru.akumakeito.effectivemobile_test.data.dto.ProductResponse

interface ProductApiService {

    @GET("v3/97e721a7-0a66-4cae-b445-83cc0bcf9010")
    suspend fun getAllProducts() : ProductResponse

}