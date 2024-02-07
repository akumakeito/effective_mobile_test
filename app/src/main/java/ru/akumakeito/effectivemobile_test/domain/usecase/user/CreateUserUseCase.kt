package ru.akumakeito.effectivemobile_test.domain.usecase.user

import ru.akumakeito.effectivemobile_test.domain.repository.UserRepository
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(name: String, surname: String, phoneNumber: String) = repository.createUser(name, surname, phoneNumber)
}