package com.awecode.thupraiisbnscanner.view.setting

import android.view.View
import androidx.core.content.ContextCompat
import com.awecode.thupraiisbnscanner.R
import com.awecode.thupraiisbnscanner.model.Setting
import com.awecode.thupraiisbnscanner.utils.showToast
import com.awecode.thupraiisbnscanner.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : BaseActivity() {

    override val layoutId = R.layout.activity_setting

    override fun initView() {
        super.initView()

        setupToolbar()

        //populate saved delay time
        delayEditText.setText(Setting.delayTime.toString())
        enablePriceCheckBox.isChecked = Setting.priceInputStatus

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun saveBtnClick(view: View?) {

        delayEditText.error = null

        if (checkDelayInput(delayEditText.text.toString()))
            saveSetting()
        else {
            delayEditText.error = getString(R.string.please_enter_valid_delay_time)
            showToast(getString(R.string.please_enter_valid_delay_time))
        }

    }

    private fun saveSetting() {
        Setting.delayTime = delayEditText.text.toString().toLong()
        Setting.priceInputStatus = enablePriceCheckBox.isChecked
        showToast("Settings saved!")
    }


    private fun checkDelayInput(delayStr: String): Boolean {
        if (delayStr.isNullOrEmpty())
            return false

        val delay = delayStr.toInt()
        if (delay > 0)
            return true
        return false
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.setting)
        //set title color
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))

        //setup back arrow
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)


    }
}