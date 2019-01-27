package com.awecode.thupraiisbnscanner.view.history

import android.os.Handler
import androidx.recyclerview.widget.LinearLayoutManager
import com.awecode.thupraiisbnscanner.R
import com.awecode.thupraiisbnscanner.view.base.BaseActivity
import com.awecode.thupraiisbnscanner.db.BarcodeDataBase
import com.awecode.thupraiisbnscanner.db.entity.BarcodeData
import com.awecode.thupraiisbnscanner.utils.DbWorkerThread
import kotlinx.android.synthetic.main.activity_barcode_history.*


class BarcodeHistoryActivity : BaseActivity() {

    private val TAG = BarcodeHistoryActivity::class.java.simpleName

    override val layoutId = R.layout.activity_barcode_history

    private lateinit var mDbWorkerThread: DbWorkerThread

    private val mUiHandler = Handler()

    private var mDb: BarcodeDataBase? = null

    override fun initView() {
        super.initView()

        initializeWorkerThread()
        mDb = BarcodeDataBase.getInstance(this)

        mUiHandler.postDelayed({
            fetchAllBarcodeData()
        }, 5000)

    }

    private fun initializeWorkerThread() {
        mDbWorkerThread = DbWorkerThread("dbWorkerThread")
        mDbWorkerThread.start()
    }

    private fun fetchAllBarcodeData() {
        val task = Runnable {
            val barcodeDatas =
                    mDb?.barcodeDataDao()?.getAll()
            if (barcodeDatas != null && barcodeDatas.isNotEmpty())
                mUiHandler.post {
                    setupRecyclerView(barcodeDatas)
                }


        }

        mDbWorkerThread.postTask(task)
    }


    private fun setupRecyclerView(barcodeDatas: List<BarcodeData>) {
        val historyAdapter = HistoryAdapter(barcodeDatas) {

        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = historyAdapter
    }
}