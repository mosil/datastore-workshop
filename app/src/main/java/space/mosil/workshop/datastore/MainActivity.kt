package space.mosil.workshop.datastore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

  lateinit var edtAccount: TextInputEditText
  lateinit var txtDeviceId: AppCompatTextView
  lateinit var btnGenerateDeviceId: AppCompatButton
  lateinit var btnSave: AppCompatButton
  lateinit var btnClear: AppCompatButton

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    edtAccount = findViewById(R.id.edt_main_account)
    txtDeviceId = findViewById(R.id.txt_main_device_id)
    btnGenerateDeviceId = findViewById(R.id.btn_main_generate_device_id)
    btnSave = findViewById(R.id.btn_main_save)
    btnClear = findViewById(R.id.btn_main_clear)

  }
}