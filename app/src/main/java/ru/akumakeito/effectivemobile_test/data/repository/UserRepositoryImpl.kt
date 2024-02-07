package ru.akumakeito.effectivemobile_test.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import ru.akumakeito.effectivemobile_test.domain.repository.UserRepository
import ru.akumakeito.util.Constants.Companion.USER_NAME
import ru.akumakeito.util.Constants.Companion.USER_PHONE
import ru.akumakeito.util.Constants.Companion.USER_SURNAME
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val prefs: DataStore<Preferences>
) : UserRepository {

    override suspend fun createUser(name: String, surname: String, phoneNumber: String) {
        prefs.edit {
            it[stringPreferencesKey(USER_NAME)] = name
            it[stringPreferencesKey(USER_SURNAME)] = surname
            it[stringPreferencesKey(USER_PHONE)] = phoneNumber
            it[booleanPreferencesKey("user_signed_in")] = true

            Log.d("userdata", "prefs create ${it}")
        }



    }

    override suspend fun deleteUser() {
        prefs.edit {
            it.clear()
            Log.d("userdata", "prefs delete ${it}")
        }
    }
}