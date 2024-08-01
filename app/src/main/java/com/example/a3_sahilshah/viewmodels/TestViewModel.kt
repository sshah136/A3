package com.example.a3_sahilshah.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.a3_sahilshah.dao.TestDao
import com.example.a3_sahilshah.entity.Test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TestViewModel (private val testDao: TestDao): ViewModel() {

    fun insertTest(test: Test) {
        viewModelScope.launch(Dispatchers.IO) {
            testDao.insertTest(test)
        }
    }

    fun getTestsForPatient(patientId: Int): Flow<List<Test?>> {
        return testDao.getTestsForPatient(patientId)
    }
}

class TestViewModelFactory(private val testDao: TestDao): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TestViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TestViewModel(testDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}