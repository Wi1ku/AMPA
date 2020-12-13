package com.example.bmi.Room

import androidx.room.*

@Dao
interface RecordDao {
    @Query("SELECT * FROM bmirecord")
    fun getAll(): List<BmiRecord>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg records: BmiRecord)

    @Query("SELECT count(*) FROM bmirecord")
    fun count(): Int

    @Query("SELECT * FROM bmirecord WHERE time = (SELECT MIN(time) FROM bmirecord)")
    fun getOldest(): BmiRecord

    @Delete
    fun delete(record: BmiRecord)

}