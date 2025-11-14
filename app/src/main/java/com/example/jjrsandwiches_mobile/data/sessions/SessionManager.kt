package com.example.jjrsandwiches_mobile.data.sessions

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.jjrsandwiches_mobile.data.model.User
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class SessionManager(context: Context) {
    private val dataStore = context.dataStore

    companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        private val USER_KEY = stringPreferencesKey("user")
    }

    suspend fun saveSession(accessToken: String, user: User) {
        dataStore.edit {
            it[ACCESS_TOKEN_KEY] = accessToken
            it[USER_KEY] = Gson().toJson(user)
        }
    }

    fun getAccessToken(): Flow<String?> {
        return dataStore.data.map {
            it[ACCESS_TOKEN_KEY]
        }
    }

    fun getUser(): Flow<User?> {
        return dataStore.data.map {
            val userJson = it[USER_KEY]
            if (userJson != null) {
                Gson().fromJson(userJson, User::class.java)
            } else {
                null
            }
        }
    }

    suspend fun clearSession() {
        dataStore.edit {
            it.clear()
        }
    }
}