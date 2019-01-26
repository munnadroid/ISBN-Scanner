package com.awecode.thupraiisbnscanner.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)

        initView()
    }

    abstract val layoutId: Int

    open fun initView() {

    }
}