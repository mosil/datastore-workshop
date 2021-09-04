package space.mosil.workshop.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class DataStoreRepo(private val dataStore: DataStore<Preferences>) {

  private object PreferencesKeys {
    val ACCOUNT = stringPreferencesKey("account")
    val ID = stringPreferencesKey("device_id")
    val TIME = longPreferencesKey("time")
  }

  val userDataFlow: Flow<UserData> = dataStore.data.catch { exception ->
    if (exception is IOException) {
      emit(emptyPreferences())
    } else {
      throw exception
    }
  }.map { preferences ->
    val account = preferences[PreferencesKeys.ACCOUNT] ?: ""
    val id = preferences[PreferencesKeys.ID] ?: ""
    UserData(account, id)
  }

  suspend fun login(userData: UserData) {
    dataStore.edit { preferences ->
      preferences[PreferencesKeys.ACCOUNT] = userData.name
      preferences[PreferencesKeys.ID] = userData.deviceId
      preferences[PreferencesKeys.TIME] = userData.modifyTime
    }
  }

  suspend fun logout() {
    dataStore.edit { preferences ->
      preferences.clear()
    }
  }
}