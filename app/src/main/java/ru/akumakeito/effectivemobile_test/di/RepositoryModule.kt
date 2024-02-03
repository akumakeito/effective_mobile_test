package ru.akumakeito.effectivemobile_test.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.akumakeito.effectivemobile_test.data.repository.ProductsRepositoryImpl
import ru.akumakeito.effectivemobile_test.domain.repository.ProductRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindProductsRepository(productsRepositoryImpl: ProductsRepositoryImpl): ProductRepository
}