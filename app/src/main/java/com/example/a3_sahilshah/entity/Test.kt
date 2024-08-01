package com.example.a3_sahilshah.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tests", foreignKeys =
    [ForeignKey(
        entity = Nurse::class,
        parentColumns = arrayOf("nurseId"),
        childColumns = arrayOf("nurseId")
    ),
        ForeignKey(
            entity = Patient::class,
            parentColumns = arrayOf("patientId"),
            childColumns = arrayOf("patientId")
        )],
    indices = [Index(value = ["nurseId"]), Index(value = ["patientId"])]
)
data class Test (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "testId")
    var testId: Int = 0,
    @ColumnInfo(name = "patientId") val patientId: Int = 0,
    @ColumnInfo(name = "nurseId") val nurseId: Int,
    @ColumnInfo(name = "BPL") val bpl: Int,
    @ColumnInfo(name = "BPH") val bph: Int,
    @ColumnInfo(name = "temperature") val temperature: Float
)