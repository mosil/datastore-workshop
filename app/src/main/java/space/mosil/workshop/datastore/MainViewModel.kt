package space.mosil.workshop.datastore

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val repo: DataStoreRepo) : ViewModel() {
  private val dsRepoFlow = repo.userDataFlow
  var user: MutableLiveData<UserData> = MutableLiveData()

  init {
    viewModelScope.launch(Dispatchers.IO) {
      dsRepoFlow.collect { repo ->
        withContext(Dispatchers.Main) {
          if (user.value == null) {
            user.value = UserData(repo.name, repo.deviceId)
          } else {
            val temp = UserData(repo.name, repo.deviceId)
            user.value = temp
          }
        }
      }
    }
  }

  fun login(data: UserData) {
    viewModelScope.launch(Dispatchers.IO) { repo.login(data) }
  }

  fun logout() {
    viewModelScope.launch(Dispatchers.IO) { repo.logout() }
  }

  fun setDeviceId(id: String) {
    viewModelScope.launch(Dispatchers.IO) { repo.update(id) }
  }
}

class MainViewModelFactory(
  private val repo: DataStoreRepo
) : ViewModelProvider.Factory {
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    @Suppress("UNCHECKED_CAST")
    return MainViewModel(repo) as T
  }
}