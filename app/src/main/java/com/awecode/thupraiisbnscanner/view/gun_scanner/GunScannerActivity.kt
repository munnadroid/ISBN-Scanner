package com.awecode.thupraiisbnscanner.view.gun_scanner

import android.content.Context
import android.content.Intent
import android.view.KeyEvent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.awecode.thupraiisbnscanner.R
import com.awecode.thupraiisbnscanner.model.Currency
import com.awecode.thupraiisbnscanner.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_gun_scanner.*
import com.awecode.thupraiisbnscanner.db.BarcodeDataBase
import com.awecode.thupraiisbnscanner.db.entity.BarcodeData
import com.awecode.thupraiisbnscanner.utils.CommonUtils
import com.awecode.thupraiisbnscanner.utils.DbWorkerThread
import com.awecode.thupraiisbnscanner.utils.logv


class GunScannerActivity : BaseActivity() {

    override val layoutId = R.layout.activity_gun_scanner

    private var mDb: BarcodeDataBase? = null

    private lateinit var mDbWorkerThread: DbWorkerThread


    override fun initView() {
        super.initView()

        initializeWorkerThread()
        mDb = BarcodeDataBase.getInstance(this)

        showKeyboardPriceInputFocus()
        setupCurrencyListAdapter()
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event.characters != null && !event.characters.isEmpty())
            logv("testing the barcode value")
        return super.dispatchKeyEvent(event)

    }

    fun saveBtnClick(view: View?) {
        saveBarcodeData()
    }

    private fun saveBarcodeData() {
        insertBarcodeDataInDb(BarcodeData(null, isbnEditText.text.toString(),
                priceEditText.text.toString(),
                CommonUtils.getTodayStringDate(),
                null))
    }

    /**
     * Insert barcode data into DB
     */
    private fun insertBarcodeDataInDb(barcodeData: BarcodeData) {
        val task = Runnable { mDb?.barcodeDataDao()?.insert(barcodeData) }
        mDbWorkerThread.postTask(task)
    }

    private fun initializeWorkerThread() {
        mDbWorkerThread = DbWorkerThread("dbWorkerThread")
        mDbWorkerThread.start()
    }


    private fun showKeyboardPriceInputFocus() {
        priceEditText.setOnFocusChangeListener { view, focused ->
            if (focused)
                CommonUtils.showKeyboard(this)
        }
    }

    private fun setupCurrencyListAdapter() {
        val adapter = CurrencyListAdapter(getCurrencyList()) {
            //radiobutton click listener
        }

        currencyRecyclerView.layoutManager = LinearLayoutManager(this)
        currencyRecyclerView.adapter = adapter
    }

    private fun getCurrencyList(): List<Currency> {
        val list: MutableList<Currency> = ArrayList<Currency>()
        list.apply {
            add(Currency("Nepalese Rupee", "NPR", true))
            add(Currency("Indian Rupee", "INR"))
            add(Currency("US Dollar", "USD"))
        }

        return list
    }

    companion object {

        fun start(context: Context) {
            context.startActivity(Intent(context, GunScannerActivity::class.java))
        }

    }
}