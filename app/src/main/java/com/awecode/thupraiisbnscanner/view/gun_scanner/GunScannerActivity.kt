package com.awecode.thupraiisbnscanner.view.gun_scanner

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.awecode.thupraiisbnscanner.R
import com.awecode.thupraiisbnscanner.model.Currency
import com.awecode.thupraiisbnscanner.utils.showToast
import com.awecode.thupraiisbnscanner.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_gun_scanner.*

class GunScannerActivity : BaseActivity() {

    override val layoutId = R.layout.activity_gun_scanner

    override fun initView() {
        super.initView()

        setupCurrencyListAdapter()
    }

    private fun setupCurrencyListAdapter() {
        val adapter = CurrencyListAdapter(getCurrencyList()) {
            showToast(it.name.toString())
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