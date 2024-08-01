package com.example.a3_sahilshah

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.a3_sahilshah.databinding.ActivityViewTestBinding
import com.example.a3_sahilshah.viewmodels.PatientViewModel
import com.example.a3_sahilshah.viewmodels.TestViewModel
import com.example.a3_sahilshah.viewmodels.TestViewModelFactory
import kotlinx.coroutines.launch

class ViewTestActivity : AppCompatActivity() {

    private lateinit var testViewModel: TestViewModel
    private lateinit var binding: ActivityViewTestBinding
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityViewTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getDatabase(applicationContext)
        testViewModel = ViewModelProvider(
            this,
            factory = TestViewModelFactory(database.testDao())
        ).get(TestViewModel::class.java)

        binding.btnViewTests.setOnClickListener {
            val patientId = binding.etPatientId.text.toString().toInt()

            lifecycleScope.launch {
                val testResults = testViewModel.getTestsForPatient(patientId).collect{
                    testResults ->
                    if(testResults.isNotEmpty()){
                        val testResultString = testResults.joinToString("\n") {test->
                            "Nurse ID: ${test?.nurseId}, BPH: ${test?.bph}, BPL: ${test?.bpl}, Temperature: ${test?.temperature}"
                        }
                        binding.tvTestResults.text = testResultString
                    }else{
                        binding.tvTestResults.text = "No tests found for the given patient ID."

                    }
                }
            }
        }
    }
}