package ru.akumakeito.effectivemobile_test.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.akumakeito.effectivemobile_test.data.dao.Converters
import ru.akumakeito.network.ProductApiService
import javax.inject.Singleton

const val BASE_URL = "https://run.mocky.io/"

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    fun providesGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideConverters(gson: Gson) : Converters = Converters(gson)
    @Provides
    fun provideProductApiService(gson: Gson): ProductApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(ProductApiService::class.java)


}
