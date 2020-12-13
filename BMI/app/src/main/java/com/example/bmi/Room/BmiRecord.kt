package com.example.bmi.Room

import android.content.Context
import androidx.room.*
import kotlinx.serialization.Serializable
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

@Entity
data class BmiRecord(
    @ColumnInfo val bmi:Double,
    @ColumnInfo val height: Double,
    @ColumnInfo val mass:Double,
    @ColumnInfo val isImperial:Boolean,
    @PrimaryKey val time: Long)

