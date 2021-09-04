package space.mosil.workshop.datastore

import android.content.Context

class DataRepo(context: Context) {
  companion object {
    private val KEY_NAME = "gdg_demo"
    private val KEY_USER = "account"
    private val KEY_ID = "device_id"
    private val KEY_TIME = "time"
  }

  private val preference = context.getSharedPreferences(KEY_NAME, Context.MODE_PRIVATE)

  fun setUser(data: UserData) {
    preference.edit().apply {
      putString(KEY_USER, data.name)
      putString(KEY_ID, data.deviceId)
      putLong(KEY_TIME, data.modifyTime)
    }.apply()
  }

  fun getUser(): UserData {
    val data = UserData(
      preference.getString(KEY_USER, "")!!,
      preference.getString(KEY_ID, "")!!,
    )
    data.modifyTime = preference.getLong(KEY_TIME, 0)

    return data
  }
}