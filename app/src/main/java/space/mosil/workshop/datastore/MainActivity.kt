package space.mosil.workshop.datastore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class MainActivity : AppCompatActivity() {

  lateinit var edtAccount: TextInputEditText
  lateinit var txtDeviceId: AppCompatTextView
  lateinit var btnGenerateDeviceId: AppCompatButton
  lateinit var btnSave: AppCompatButton
  lateinit var btnClear: AppCompatButton

  lateinit var repo: DataRepo

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    edtAccount = findViewById(R.id.edt_main_account)
    txtDeviceId = findViewById(R.id.txt_main_device_id)
    btnGenerateDeviceId = findViewById(R.id.btn_main_generate_device_id)
    btnSave = findViewById(R.id.btn_main_save)
    btnClear = findViewById(R.id.btn_main_clear)

    repo = DataRepo(this)

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
    val user = UserData(account, txtDeviceId.text.toString())
    repo.setUser(user)
  }

  private fun setData() {
    val user = repo.getUser()
    if (user.isLogin()) {
      edtAccount.setText(user.name)
      txtDeviceId.setText(user.deviceId)
    }
  }
}