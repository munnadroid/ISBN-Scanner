package com.awecode.thupraiisbnscanner.history

import androidx.recyclerview.widget.LinearLayoutManager
import com.awecode.thupraiisbnscanner.R
import com.awecode.thupraiisbnscanner.base.BaseActivity
import com.awecode.thupraiisbnscanner.db.entity.BarcodeData
import kotlinx.android.synthetic.main.activity_barcode_history.*


class BarcodeHistoryActivity : BaseActivity() {

    override val layoutId = R.layout.activity_barcode_history

    override fun initView() {
        super.initView()

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val historyAdapter = HistoryAdapter(getDataList(), {

        })
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = historyAdapter
    }

    private fun getDataList(): List<BarcodeData> {
        val list = ArrayList<BarcodeData>()
        list.apply {
            add(BarcodeData(1, "121212121212", "01/10/2018 04:44", null))
            add(BarcodeData(1, "121212121212", "01/10/2018 04:44", null))
            add(BarcodeData(1, "121212121212", "01/10/2018 04:44", null))
            add(BarcodeData(1, "121212121212", "01/10/2018 04:44", null))
            add(BarcodeData(1, "121212121212", "01/10/2018 04:44", null))
            add(BarcodeData(1, "121212121212", "01/10/2018 04:44", null))
            add(BarcodeData(1, "121212121212", "01/10/2018 04:44", null))
        }
        return list
    }
}