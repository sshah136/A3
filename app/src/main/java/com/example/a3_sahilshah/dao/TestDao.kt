package com.example.a3_sahilshah.dao


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.a3_sahilshah.entity.Test
import kotlinx.coroutines.flow.Flow

@Dao
interface TestDao {
//    @Query("SELECT * FROM tests")
//    fun getAllTests(): Flow<List<Test>>

    @Insert
    suspend fun insertTest(test: Test)

//    @Update
//    suspend fun updateTest(test: Test)

    @Query("SELECT * FROM tests WHERE patientId = :patientId")
    fun getTestsForPatient(patientId: Int): Flow<List<Test>>

    @Query("SELECT * FROM tests WHERE testId = :testId")
    fun getTest(testId: Int): Flow<List<Test>>
}