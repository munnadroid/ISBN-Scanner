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
import android.widget.TextView.OnEditorActionListener
import android.view.inputmethod.EditorInfo
import kotlinx.android.synthetic.main.currency_item.view.*


class GunScannerActivity : BaseActivity() {

    override val layoutId = R.layout.activity_gun_scanner

    private var mDb: BarcodeDataBase? = null

    private lateinit var mDbWorkerThread: DbWorkerThread

    private var mSelectedCurrency: Currency? = null
    private var barcode: String? = null


    override fun initView() {
        super.initView()

        initializeWorkerThread()
        mDb = BarcodeDataBase.getInstance(this)

        showKeyboardPriceInputFocus()
        setupImeActionInPrice()
        setupCurrencyListAdapter()
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {

        //barcode scanner
        val c = event.unicodeChar
        //accept only 0..9 and ENTER
        if (c >= 48 && c <= 57 || c == 10) {
            if (event.action == 0) {
                if (c >= 48 && c <= 57)
                    barcode += "" + c.toChar()
                else {
                    if (!barcode.equals("")) {
                        val b = barcode
                        barcode = ""
                        runOnUiThread {
                            // showToast("testing")
                            isbnScanFinished()
                        }
                    }
                }
            }
            return true
        }
        return super.dispatchKeyEvent(event)
    }

    private fun isbnScanFinished() {
        saveBarcodeData()
    }

    fun saveBtnClick(view: View?) {
        saveBarcodeData()
    }

    private fun saveBarcodeData() {
        insertBarcodeDataInDb(BarcodeData(null, isbnEditText.text.toString(),
                mSelectedCurrency?.code,
                priceEditText.text.toString(),
                CommonUtils.getTodayStringDate(),
                null))

        resetFormFields()

    }

    private fun resetFormFields() {
        priceEditText.setText("")
        isbnEditText.setText("")
        priceEditText.requestFocus()
        CommonUtils.showKeyboard(this, priceEditText)
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

    /**
     * Request focus in price edittext
     *
     */
    private fun showKeyboardPriceInputFocus() {
        priceEditText.requestFocus()
        CommonUtils.showKeyboard(this)
    }

    /**
     * Move focus to ISBN field when softkeyboard enter button clicked in price field
     */
    private fun setupImeActionInPrice() {
        priceEditText.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                isbnEditText.requestFocus() //move focus to isbn
                CommonUtils.showKeyboard(this, priceEditText)//show keyboard
                return@OnEditorActionListener true

            }
            false
        })

        isbnEditText.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                saveBarcodeData()
                return@OnEditorActionListener true

            }
            false
        })


    }

    private fun setupCurrencyListAdapter() {
        val adapter = CurrencyListAdapter(getCurrencyList()) {
            //radiobutton click listener
            mSelectedCurrency = it
        }

        currencyRecyclerView.layoutManager = LinearLayoutManager(this)
        currencyRecyclerView.adapter = adapter

        val viewHolder = currencyRecyclerView.findViewHolderForAdapterPosition(0)
        viewHolder?.itemView?.currencyRadioButton?.isChecked = true
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