package ru.akumakeito.presentation.viewmodel

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.akumakeito.effectivemobile_test.domain.model.User
import ru.akumakeito.effectivemobile_test.domain.repository.UserRepository
import ru.akumakeito.effectivemobile_test.domain.usecase.product.DeleteProductsUseCase
import ru.akumakeito.effectivemobile_test.domain.usecase.user.CreateUserUseCase
import ru.akumakeito.effectivemobile_test.domain.usecase.user.DeleteUserUseCase
import javax.inject.Inject

private val emptyUser = User(
    "",
    "",
    ""
)
@HiltViewModel
class UserViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val prefs : DataStore<Preferences>,
    private val deleteProductsUseCase: DeleteProductsUseCase,
    repository: UserRepository
) : ViewModel() {

    private val _userData = prefs.data.asLiveData()
    val userData = _userData

    val isSigned = prefs.data.map { it[booleanPreferencesKey("user_signed_in")] ?: false }.asLiveData()

    private val _userRegistryInput = MutableLiveData(emptyUser)
    val userRegistry = _userRegistryInput

    fun createUser(name: String, surname: String, phoneNumber: String) =
        viewModelScope.launch {
            Log.d("registrationprob", "vm name ${name} surname ${surname} phone ${phoneNumber}")

            createUserUseCase.invoke(name, surname, phoneNumber)
        }

    fun exit() = viewModelScope.launch {
        deleteUserUseCase.invoke()
        deleteProductsUseCase.invoke()

    }

    fun setUser(name: String = "", surname: String = "", phoneNumber: String = "") {
        if (name.isNotBlank()) {
            _userRegistryInput.value = _userRegistryInput.value?.copy(name = name)
        }

        if (surname.isNotBlank()) {
            _userRegistryInput.value = _userRegistryInput.value?.copy(surname = surname)
        }

        if (phoneNumber.isNotBlank()) {
            _userRegistryInput.value = _userRegistryInput.value?.copy(phoneNumber = phoneNumber)
        }

    }


}