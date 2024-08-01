package com.example.a3_sahilshah

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.a3_sahilshah.dao.NurseDao
import com.example.a3_sahilshah.dao.PatientDao
import com.example.a3_sahilshah.dao.TestDao
import com.example.a3_sahilshah.entity.Nurse
import com.example.a3_sahilshah.entity.Patient
import com.example.a3_sahilshah.entity.Test

@Database(entities = [Nurse::class,Patient::class, Test::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase(){

    abstract fun nurseDao(): NurseDao
    abstract fun patientDao(): PatientDao
    abstract fun testDao(): TestDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context) : AppDatabase{
            if(INSTANCE == null){
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "hospitalDB"
                    ).build()
                }

            }
            return  INSTANCE!!
        }
    }
}