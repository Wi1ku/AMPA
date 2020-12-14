package com.example.bmi.Room

import androidx.room.*

@Dao
interface RecordDao {
    @Query("SELECT * FROM bmirecord")
    suspend fun getAll(): List<BmiRecord>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg records: BmiRecord)

    @Query("SELECT count(*) FROM bmirecord")
    suspend fun count(): Int

    @Query("SELECT * FROM bmirecord WHERE time = (SELECT MIN(time) FROM bmirecord)")
    suspend fun getOldest(): BmiRecord

    @Delete
    suspend fun delete(record: BmiRecord)

}