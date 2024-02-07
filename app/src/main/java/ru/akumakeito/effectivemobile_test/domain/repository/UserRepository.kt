package ru.akumakeito.effectivemobile_test.domain.repository

interface UserRepository {


    suspend fun createUser(name: String, surname: String, phoneNumber: String)
    suspend fun deleteUser()
}