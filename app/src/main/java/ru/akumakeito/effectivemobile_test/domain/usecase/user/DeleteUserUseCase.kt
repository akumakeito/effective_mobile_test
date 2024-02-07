package ru.akumakeito.effectivemobile_test.domain.usecase.user

import ru.akumakeito.effectivemobile_test.domain.repository.UserRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke() = repository.deleteUser()
}