package com.example.a3_sahilshah.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.a3_sahilshah.dao.PatientDao
import com.example.a3_sahilshah.entity.Patient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PatientViewModel(private val patientDao: PatientDao) : ViewModel() {

    fun insertPatient(patient: Patient) {
        viewModelScope.launch(Dispatchers.IO) {
            patientDao.insertPatient(patient)
        }
    }

    fun updatePatient(patient: Patient) {
        viewModelScope.launch(Dispatchers.IO) {
            patientDao.updatePatient(patient)
        }
    }

    suspend fun getPatient(patientId: Int):Patient? {
        return patientDao.getPatient(patientId)
    }
}

class PatientViewModelFactory(private val patientDao: PatientDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PatientViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PatientViewModel(patientDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}