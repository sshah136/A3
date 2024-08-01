package com.example.a3_sahilshah

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.a3_sahilshah.dao.PatientDao
import com.example.a3_sahilshah.databinding.ActivityPatientBinding
import com.example.a3_sahilshah.entity.Patient
import com.example.a3_sahilshah.viewmodels.NurseViewModel
import com.example.a3_sahilshah.viewmodels.NurseViewModelFactory
import com.example.a3_sahilshah.viewmodels.PatientViewModel
import com.example.a3_sahilshah.viewmodels.PatientViewModelFactory
import kotlinx.coroutines.launch

class PatientActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPatientBinding
    private lateinit var patientViewModel: PatientViewModel
    private lateinit var database: AppDatabase
    private var nurseId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getDatabase(applicationContext)
        patientViewModel = ViewModelProvider(
            this,
            factory = PatientViewModelFactory(database.patientDao())
        ).get(PatientViewModel::class.java)

        binding.btnSavePatient.setOnClickListener {

            val firstName = binding.etFirstName.text.toString()
            val lastName = binding.etLastName.text.toString()
            val department = binding.etDepartment.text.toString()
            val room = binding.etRoom.text.toString()
            val sharedPref = getSharedPreferences("nurseId", Context.MODE_PRIVATE)
            nurseId = sharedPref.getInt("nurseId", -1)


            // Validate input
            if (firstName.isEmpty()|| lastName.isEmpty() || department.isEmpty() || room.isEmpty()) {
                Toast.makeText(
                    this@PatientActivity,
                    "Please enter all details",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            val roomId = room.toInt()

            lifecycleScope.launch {
                patientViewModel.insertPatient(
                    Patient(0, firstName, lastName, department, room = roomId, nurseId = nurseId))
                Toast.makeText(
                    this@PatientActivity,
                    "Patient Added Successfully",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.btnViewPatient.setOnClickListener {
            val intent = Intent(this,UpdateInfoActivity::class.java)
            startActivity(intent)
        }

        binding.btnEnterTest.setOnClickListener {
            val intent = Intent(this,TestActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener {
            clearNurseIdFromSharedPreferences()
            val intent = Intent(this,MainLoginActivity::class.java)
            startActivity(intent)
        }

    }
    private fun clearNurseIdFromSharedPreferences() {
        val sharedPref = getSharedPreferences("nurseId", Context.MODE_PRIVATE)
        sharedPref.edit().remove("nurseId").apply()
    }

}