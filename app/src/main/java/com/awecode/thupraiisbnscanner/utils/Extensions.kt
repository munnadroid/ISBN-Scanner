package com.awecode.thupraiisbnscanner.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.awecode.thupraiisbnscanner.BuildConfig

fun Context.showToast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length)
            .show()
}

fun Activity.logv(message: String) {
    if (BuildConfig.DEBUG) Log.v(this::class.java.simpleName, message)
}

fun Fragment.logv(message: String) {
    if (BuildConfig.DEBUG) Log.v(this::class.java.simpleName, message)
}