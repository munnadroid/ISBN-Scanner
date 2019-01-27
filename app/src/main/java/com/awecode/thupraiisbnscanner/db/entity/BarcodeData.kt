package com.awecode.thupraiisbnscanner.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "barcodeData")
data class BarcodeData(@PrimaryKey(autoGenerate = true) var id: Long?,
                       @ColumnInfo(name = "barcode") var barcode: String,
                       @ColumnInfo(name = "date") var date: String,
                       @ColumnInfo(typeAffinity = ColumnInfo.BLOB, name = "image") var image: ByteArray?)