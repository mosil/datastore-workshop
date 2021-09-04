package space.mosil.workshop.datastore

import java.util.*

data class UserData(
  var name: String = "",
  var deviceId: String = ""
) {
  var modifyTime: Long = 0
    get() {
      return Date().time
    }

  fun isLogin(): Boolean {
    return name.isNotEmpty() && deviceId.isNotEmpty()
  }
}
