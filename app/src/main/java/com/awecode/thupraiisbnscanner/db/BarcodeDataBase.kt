package com.awecode.thupraiisbnscanner.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.awecode.thupraiisbnscanner.db.daos.BarcodeDataDao
import com.awecode.thupraiisbnscanner.db.entity.BarcodeData
import com.awecode.thupraiisbnscanner.utils.Constants

@Database(entities = arrayOf(BarcodeData::class), version = 1)
abstract class BarcodeDataBase : RoomDatabase() {

    abstract fun barcodeDataDao(): BarcodeDataDao

    companion object {

        private var INSTANCE: BarcodeDataBase? = null

        fun getInstance(context: Context): BarcodeDataBase? {
            if (INSTANCE == null) {
                synchronized(BarcodeDataBase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            BarcodeDataBase::class.java, Constants.DATABASE_NAME)
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}