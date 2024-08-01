package com.example.a3_sahilshah.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.a3_sahilshah.entity.Patient
import kotlinx.coroutines.flow.Flow
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PatientDao {
//    @Query("SELECT * FROM patients")
//    fun getAllPatients(): LiveData<List<Patient>>

    @Insert
    suspend fun insertPatient(patient: Patient)

    @Update
    suspend fun updatePatient(patient: Patient)

    @Query("SELECT * FROM patients WHERE patientId = :patientId")
    suspend fun getPatient(patientId: Int): Patient?

//    @Query("DELETE FROM patients WHERE patientId = :patientId")
//    fun deletePatient(patientId: Int): Int
}