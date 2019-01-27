package com.awecode.thupraiisbnscanner.view.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tbruyelle.rxpermissions2.RxPermissions


abstract class BaseActivity : AppCompatActivity() {

    var rxPermissions: RxPermissions? = null;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        rxPermissions = RxPermissions(this)
        initView()
    }

    abstract val layoutId: Int

    open fun initView() {

    }
}