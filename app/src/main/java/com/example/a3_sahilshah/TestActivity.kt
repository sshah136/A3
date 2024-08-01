package com.example.a3_sahilshah

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import com.example.a3_sahilshah.databinding.ActivityTestBinding
import com.example.a3_sahilshah.entity.Patient
import com.example.a3_sahilshah.entity.Test
import com.example.a3_sahilshah.viewmodels.PatientViewModel
import com.example.a3_sahilshah.viewmodels.PatientViewModelFactory
import com.example.a3_sahilshah.viewmodels.TestViewModel
import com.example.a3_sahilshah.viewmodels.TestViewModelFactory
import kotlinx.coroutines.launch

class TestActivity : AppCompatActivity() {


    private lateinit var binding: ActivityTestBinding
    private lateinit var database: AppDatabase
    private lateinit var testViewModel: TestViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getDatabase(applicationContext)
        testViewModel = ViewModelProvider(
            this,
            factory = TestViewModelFactory(database.testDao())
        ).get(TestViewModel::class.java)



        binding.btnSaveTest.setOnClickListener {
            val patientId = binding.etPatientId.text.toString()
            val bpl = binding.etBPL.text.toString()
            val bph = binding.etBPH.text.toString()
            val temperature = binding.etTemperature.text.toString()
            val sharedPref = getSharedPreferences("nurseId", Context.MODE_PRIVATE)
            val nurseId = sharedPref.getInt("nurseId", -1)


            // Validate input
            if (patientId.isEmpty()|| bpl.isEmpty() || bph.isEmpty() || temperature.isEmpty()) {
                Toast.makeText(
                    this@TestActivity,
                    "Please enter all details",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }


            lifecycleScope.launch {
                testViewModel.insertTest(
                    Test(
                        patientId = patientId.toInt(),
                        nurseId = nurseId,
                        bpl = bpl.toInt(),
                        bph = bph.toInt(),
                        temperature = temperature.toFloat()
                    )
                )
                Toast.makeText(
                    this@TestActivity,
                    "Test Results Added",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.btnViewTest.setOnClickListener {
            val intent = Intent(this,ViewTestActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener {
            val intent = Intent(this,MainLoginActivity::class.java)
            startActivity(intent)
        }
    }
}