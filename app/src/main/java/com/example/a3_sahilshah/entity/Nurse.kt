package com.example.a3_sahilshah.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nurses")
data class Nurse(
    @PrimaryKey(autoGenerate = true) val nurseId: Int = 0,
    @ColumnInfo(name = "firstName") val firstName: String,
    @ColumnInfo(name = "lastName") val lastName: String,
    @ColumnInfo(name = "department") val department: String,
    @ColumnInfo(name = "password") val password: String,
)
