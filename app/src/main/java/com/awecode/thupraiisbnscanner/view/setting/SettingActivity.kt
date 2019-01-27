package com.awecode.thupraiisbnscanner.view.setting

import androidx.core.content.ContextCompat
import com.awecode.thupraiisbnscanner.R
import com.awecode.thupraiisbnscanner.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : BaseActivity() {

    override val layoutId = R.layout.activity_setting

    override fun initView() {
        super.initView()

        setupToolbar()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
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