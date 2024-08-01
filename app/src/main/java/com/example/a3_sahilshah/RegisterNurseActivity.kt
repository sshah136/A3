package com.example.a3_sahilshah

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.a3_sahilshah.entity.Nurse
import com.example.a3_sahilshah.viewmodels.NurseViewModel
import com.example.a3_sahilshah.viewmodels.NurseViewModelFactory
import kotlinx.coroutines.launch

class RegisterNurseActivity : AppCompatActivity() {

    private lateinit var btnRegisterNurse: Button
    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etDepartment: EditText
    private lateinit var etPassword: EditText
    private lateinit var database: AppDatabase
    private lateinit var nurseViewModel: NurseViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register_nurse)

        btnRegisterNurse = findViewById(R.id.btnRegisterNurse)
        etFirstName = findViewById(R.id.etFirstName)
        etLastName = findViewById(R.id.etLastName)
        etDepartment = findViewById(R.id.etDepartment)
        etPassword = findViewById(R.id.etPassword)
        database = AppDatabase.getDatabase(applicationContext)
        nurseViewModel = ViewModelProvider(
            this,
            factory = NurseViewModelFactory(database.nurseDao())
        ).get(NurseViewModel::class.java)


        btnRegisterNurse.setOnClickListener {
            val firstName = etFirstName.text.toString()
            val lastName = etLastName.text.toString()
            val department = etDepartment.text.toString()
            val password = etPassword.text.toString()

            // Validate input
            if (firstName.isEmpty()|| lastName.isEmpty() || department.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this@RegisterNurseActivity,
                    "Please enter all details",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                nurseViewModel.insertNurse(Nurse(0,firstName,lastName,department,password))
                Toast.makeText(
                    this@RegisterNurseActivity,
                    "Registration complete",
                    Toast.LENGTH_SHORT
                ).show()
            }

            val intent = Intent(this,MainLoginActivity::class.java)
            startActivity(intent)
        }
    }


}