package space.mosil.workshop.datastore

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

private const val DATA_PREFERENCES_NAME = "data_preferences"

private val Context.dataStore by preferencesDataStore(
  name = DATA_PREFERENCES_NAME,
  produceMigrations = { context ->
    listOf(SharedPreferencesMigration(context, DATA_PREFERENCES_NAME))
  }
)

class MainActivity : AppCompatActivity() {

  lateinit var edtAccount: TextInputEditText
  lateinit var txtDeviceId: AppCompatTextView
  lateinit var btnGenerateDeviceId: AppCompatButton
  lateinit var btnSave: AppCompatButton
  lateinit var btnClear: AppCompatButton

  lateinit var repo: DataRepo

  private val dsRepo = DataStoreRepo(dataStore)
  private val dsRepoFlow = dsRepo.userDataFlow

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    edtAccount = findViewById(R.id.edt_main_account)
    txtDeviceId = findViewById(R.id.txt_main_device_id)
    btnGenerateDeviceId = findViewById(R.id.btn_main_generate_device_id)
    btnSave = findViewById(R.id.btn_main_save)
    btnClear = findViewById(R.id.btn_main_clear)

    setData()

    btnGenerateDeviceId.setOnClickListener {
      txtDeviceId.text = UUID.randomUUID().toString()
    }

    btnSave.setOnClickListener {
      val account = edtAccount.text.toString()
      when {
        account.isEmpty() -> {
          Toast.makeText(this, "帳號不可為空", Toast.LENGTH_LONG).show()
        }
        txtDeviceId.text.isEmpty() -> {
          Toast.makeText(this, "Device 不可為空", Toast.LENGTH_LONG).show()
        }
        else -> {
          saveUser(account)
        }
      }
    }

  }

  private fun saveUser(account: String) {
    lifecycleScope.launch(Dispatchers.IO) {
      val user = UserData(account, txtDeviceId.text.toString())
      dsRepo.login(user)
    }
  }

  private fun setData() {
    lifecycleScope.launch(Dispatchers.IO) {
      dsRepoFlow.collect { user ->
        if (user.isLogin()) {
          edtAccount.setText(user.name)
          txtDeviceId.text = user.deviceId
        }
      }
    }
  }
}