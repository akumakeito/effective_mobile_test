package ru.akumakeito.effectivemobile_test.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.akumakeito.effectivemobile_test.data.repository.ProductsRepositoryImpl
import ru.akumakeito.effectivemobile_test.data.repository.UserRepositoryImpl
import ru.akumakeito.effectivemobile_test.domain.repository.ProductRepository
import ru.akumakeito.effectivemobile_test.domain.repository.UserRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindProductsRepository(productsRepositoryImpl: ProductsRepositoryImpl): ProductRepository

    @Binds
    @Singleton
    fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl) : UserRepository
}