package com.example.a3_sahilshah

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.a3_sahilshah.databinding.ActivityPatientBinding
import com.example.a3_sahilshah.databinding.ActivityUpdateInfoBinding
import com.example.a3_sahilshah.entity.Patient
import com.example.a3_sahilshah.viewmodels.PatientViewModel
import com.example.a3_sahilshah.viewmodels.PatientViewModelFactory
import kotlinx.coroutines.launch

class UpdateInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateInfoBinding
    private lateinit var database: AppDatabase
    private lateinit var patientViewModel: PatientViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpdateInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getDatabase(applicationContext)
        patientViewModel = ViewModelProvider(
            this,
            factory = PatientViewModelFactory(database.patientDao())
        ).get(PatientViewModel::class.java)

        binding.btnFetchPatient.setOnClickListener {
            val patientId = binding.etPatientId.text.toString().toInt()

            lifecycleScope.launch {
                val patientInfo = patientViewModel.getPatient(patientId)
                if (patientInfo != null) {
                    binding.etFirstName.setText(patientInfo.firstName)
                    binding.etLastName.setText(patientInfo.lastName)
                    binding.etDepartment.setText(patientInfo.department)
                    binding.etRoom.setText(patientInfo.room.toString())
                } else {
                    Toast.makeText(
                        this@UpdateInfoActivity,
                        "Invalid patient ID",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }

        binding.btnUpdatePatient.setOnClickListener {
            lifecycleScope.launch {
                val patientId = binding.etPatientId.text.toString().toInt()
                val firstName = binding.etFirstName.text.toString()
                val lastName = binding.etLastName.text.toString()
                val department = binding.etDepartment.text.toString()
                val roomId = binding.etRoom.text.toString().toInt()
                val sharedPref = getSharedPreferences("nurseId", Context.MODE_PRIVATE)
                val nurseId = sharedPref.getInt("nurseId", 0)

                patientViewModel.updatePatient(
                    Patient(patientId, firstName, lastName, department, room = roomId, nurseId = nurseId)
                )
                Toast.makeText(
                    this@UpdateInfoActivity,
                    "Information Updated Successfully",
                    Toast.LENGTH_SHORT
                ).show()
            }


        }


    }
}
