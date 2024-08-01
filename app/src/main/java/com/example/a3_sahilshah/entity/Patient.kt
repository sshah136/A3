package com.example.a3_sahilshah.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "patients",
    foreignKeys = [ForeignKey(
        entity = Nurse::class,
        parentColumns = arrayOf("nurseId"),
        childColumns = arrayOf("nurseId")
    )],
    indices = [Index(value = ["nurseId"])]
)
data class Patient(
    @PrimaryKey(autoGenerate = true) val patientId: Int = 0,
    @NonNull @ColumnInfo(name = "firstName") val firstName: String,
    @NonNull @ColumnInfo(name = "lastName") val lastName: String,
    @NonNull @ColumnInfo(name = "department") val department: String,
    @NonNull @ColumnInfo(name = "nurseId") val nurseId: Int,
    @NonNull @ColumnInfo(name = "room") val room: Int
)
