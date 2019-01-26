package com.awecode.thupraiisbnscanner.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.awecode.thupraiisbnscanner.db.entity.BarcodeData

@Dao
interface BarcodeDataDao {
    
    @Query("SELECT * from barcodeData")
    fun getAll(): List<BarcodeData>

    @Insert(onConflict = REPLACE)
    fun insert(barcodeData: BarcodeData)

    @Query("DELETE from barcodeData")
    fun deleteAll()
}