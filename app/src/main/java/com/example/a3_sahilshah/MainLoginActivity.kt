package com.example.a3_sahilshah

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.a3_sahilshah.entity.Nurse
import com.example.a3_sahilshah.viewmodels.NurseViewModel
import com.example.a3_sahilshah.viewmodels.NurseViewModelFactory
import kotlinx.coroutines.launch

class MainLoginActivity : AppCompatActivity() {
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button
    private lateinit var etNurseId: TextView
    private lateinit var etPassword: TextView
    private lateinit var database: AppDatabase
    private lateinit var nurseViewModel: NurseViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_login)

        btnLogin = findViewById(R.id.btnLogin)
        btnRegister = findViewById(R.id.btnRegister)
        etNurseId = findViewById(R.id.etNurseId)
        etPassword = findViewById(R.id.etPassword)
        database = AppDatabase.getDatabase(applicationContext)
        nurseViewModel = ViewModelProvider(
            this,
            factory = NurseViewModelFactory(database.nurseDao())
        ).get(NurseViewModel::class.java)

        btnLogin.setOnClickListener {
            val nurseIdText = etNurseId.text.toString()
            val password = etPassword.text.toString()

            // Validate input
            if (nurseIdText.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this@MainLoginActivity,
                    "Please enter both ID and password",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val nurseId = nurseIdText.toInt()

            lifecycleScope.launch {
                val nurse = nurseViewModel.loginNurse(nurseId, password)
                if (nurse != null) {
                    saveNurseIdToSharedPreferences(nurseId)
                    startActivity(Intent(this@MainLoginActivity, PatientActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(
                        this@MainLoginActivity,
                        "Invalid username or password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterNurseActivity::class.java)
            startActivity(intent)
        }


    }

    private fun saveNurseIdToSharedPreferences(nurseId: Int) {
        val sharedPref = getSharedPreferences("nurseId", Context.MODE_PRIVATE)
        sharedPref.edit().putInt("nurseId", nurseId).apply()

    }



}