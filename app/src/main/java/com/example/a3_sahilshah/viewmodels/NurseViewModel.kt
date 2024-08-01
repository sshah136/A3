package com.example.a3_sahilshah.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.a3_sahilshah.dao.NurseDao
import com.example.a3_sahilshah.entity.Nurse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NurseViewModel(private val nurseDao: NurseDao) : ViewModel() {
    suspend fun insertNurse(nurse: Nurse) {
        viewModelScope.launch (Dispatchers.IO){
            nurseDao.insertNurse(nurse)
        }

    }
    suspend fun loginNurse(nurseId: Int, password: String): Nurse? {
        return nurseDao.loginNurse(nurseId, password)
    }
}

class NurseViewModelFactory(private val nurseDao: NurseDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NurseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NurseViewModel(nurseDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}