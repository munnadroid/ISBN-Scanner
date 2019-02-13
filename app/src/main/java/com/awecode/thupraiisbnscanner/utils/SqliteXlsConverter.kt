package com.awecode.thupraiisbnscanner.utils

import android.content.Context
import android.os.Environment
import com.ajts.androidmads.library.SQLiteToExcel
import com.awecode.thupraiisbnscanner.model.listener.SqliteToXlsExportListener
import java.io.File

class SqliteXlsConverter {

    var sqliteToXlsExportListener: SqliteToXlsExportListener? = null


    fun setOnExportListener(sqliteToXlsExportListener: SqliteToXlsExportListener) {
        this.sqliteToXlsExportListener = sqliteToXlsExportListener
    }

    fun exportSqliteToExcel(context: Context) {

        //create xls file
        val folderPath = Environment.getExternalStorageDirectory().path + "/Download/"
        val downloadFolder = File(folderPath)
        if (downloadFolder == null || !downloadFolder.exists())
            downloadFolder.mkdirs()

        val fileName = "barcodedata_${CommonUtils.getNowDateForFileName()}.xls"
        val file: File? = File(folderPath, fileName)
        file?.createNewFile()

//        mXlsFilePath = file?.path

        //export excel file from sqlite file
        val sqliteToExcel = SQLiteToExcel(context, Constants.DATABASE_NAME, folderPath)
        sqliteToExcel.exportAllTables(fileName, object : SQLiteToExcel.ExportListener {
            override fun onStart() {
                sqliteToXlsExportListener?.onExportStart()
            }

            override fun onCompleted(filePath: String) {
                sqliteToXlsExportListener?.onExportComplete(filePath, folderPath, fileName)

            }

            override fun onError(e: Exception) {
                e.printStackTrace()
                sqliteToXlsExportListener?.onExportError()
            }
        })
    }

}