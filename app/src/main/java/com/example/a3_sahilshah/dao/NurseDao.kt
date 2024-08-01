package com.example.a3_sahilshah.dao


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.a3_sahilshah.entity.Nurse
import kotlinx.coroutines.flow.Flow


@Dao
interface NurseDao {
//    @Query("SELECT * FROM nurses")
//    fun getAllNurses(): LiveData<List<Nurse>>

    @Insert
    suspend fun insertNurse(nurse: Nurse)

//    @Update
//    suspend fun updateNurse(nurse: Nurse)

    @Query("SELECT * FROM nurses WHERE nurseId = :nurseId")
    fun getNurse(nurseId: String): Flow<List<Nurse>>

    @Query("SELECT * FROM nurses WHERE nurseId = :nurseId AND password = :password")
    suspend fun loginNurse(nurseId: Int, password: String): Nurse?
}
