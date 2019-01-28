package com.awecode.thupraiisbnscanner.view

import android.Manifest
import android.content.Intent
import android.util.Log
import com.awecode.thupraiisbnscanner.view.base.BaseActivity
import com.google.zxing.integration.android.IntentIntegrator
import android.widget.Toast
import android.os.Environment
import android.view.*
import com.ajts.androidmads.library.SQLiteToExcel
import com.awecode.thupraiisbnscanner.db.BarcodeDataBase
import com.awecode.thupraiisbnscanner.db.entity.BarcodeData
import com.awecode.thupraiisbnscanner.view.history.BarcodeHistoryActivity
import com.awecode.thupraiisbnscanner.utils.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.util.concurrent.TimeUnit
import com.awecode.thupraiisbnscanner.R
import com.awecode.thupraiisbnscanner.view.setting.SettingActivity
import com.google.zxing.integration.android.IntentResult


class MainActivity : BaseActivity() {

    private val TAG = MainActivity::class.java.simpleName

    override val layoutId = R.layout.activity_main

    private var mDb: BarcodeDataBase? = null

    private lateinit var mDbWorkerThread: DbWorkerThread

    private var mXlsFilePath: String? = null


    override fun initView() {
        super.initView()
        initializeWorkerThread()
        mDb = BarcodeDataBase.getInstance(this)
    }


    /**
     * Get the results
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                showToast("Cancelled")
            } else
                saveBarcodeData(result) //save barcode data

        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun saveBarcodeData(result: IntentResult) {
        insertBarcodeDataInDb(BarcodeData(null, result.contents,
                CommonUtils.getTodayStringDate(),
                image = CommonUtils.readFile(result.barcodeImagePath)))

        Log.v(TAG, "Scanned value: " + result.contents)
        resumeBarcodeScanner() //resume barcode after 2 seconds
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.setting -> {
                openSettingActivity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        BarcodeDataBase.destroyInstance()
        mDbWorkerThread.quit()
        super.onDestroy()
    }

    private fun openSettingActivity() {
        startActivity(Intent(this, SettingActivity::class.java))
    }

    private fun initializeWorkerThread() {
        mDbWorkerThread = DbWorkerThread("dbWorkerThread")
        mDbWorkerThread.start()
    }

    /**
     * Resume barcode after 2 seconds
     */
    private fun resumeBarcodeScanner() {
        Observable.timer(2 * 1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {

                    startZxingScanner()
                }
    }

    /**
     * Insert barcode data into DB
     */
    private fun insertBarcodeDataInDb(barcodeData: BarcodeData) {
        val task = Runnable { mDb?.barcodeDataDao()?.insert(barcodeData) }
        mDbWorkerThread.postTask(task)
    }

    fun startBtnClick(view: View?) {
        askRunTimePermissions()
    }

    fun exportBtnClick(view: View?) {
        exportSqliteToExcel()
    }

    fun shareFileBtnClick(view: View?) {
        CommonUtils.shareFile(this, File(mXlsFilePath))
    }

    fun historyBtnClick(view: View?) {
        openHistory()
    }

    private fun openHistory() {
        startActivity(Intent(this, BarcodeHistoryActivity::class.java))
    }

    private fun exportSqliteToExcel() {

        //create xls file
        val folderPath = Environment.getExternalStorageDirectory().path + "/Download/"
        val fileName = "barcodedata_${CommonUtils.getNowDateForFileName()}.xls"
        val file: File? = File(folderPath, fileName)
        file?.createNewFile()

        mXlsFilePath = file?.path

        //export excel file from sqlite file
        val sqliteToExcel = SQLiteToExcel(this, Constants.DATABASE_NAME, folderPath)
        sqliteToExcel.exportAllTables(fileName, object : SQLiteToExcel.ExportListener {
            override fun onStart() {
                logv("xls file export started")
            }

            override fun onCompleted(filePath: String) {
                showToast("File export success. Check Download folder.")
                xlsFilePathTextView.text = "$folderPath$fileName"
                shareButton.visibility = View.VISIBLE

            }

            override fun onError(e: Exception) {
                e.printStackTrace()
                logv("xls file export error")
            }
        })
    }

    private fun askRunTimePermissions() {
        rxPermissions
                ?.request(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                ?.subscribe { granted ->
                    if (granted == true) {
                        // All requested permissions are granted
                        startZxingScanner()
                    } else {
                        // At least one permission is denied
                        Toast.makeText(this, "Please provide all permissions from application manager.", Toast.LENGTH_SHORT).show()
                    }
                }
    }

    private fun startZxingScanner() {
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        integrator.setPrompt("Scan a ISBN")
        integrator.setCameraId(0)  // Use a specific camera of the device
        integrator.setBeepEnabled(true)
        integrator.setBarcodeImageEnabled(true)
        integrator.setOrientationLocked(false)
        integrator.initiateScan()
    }


}
