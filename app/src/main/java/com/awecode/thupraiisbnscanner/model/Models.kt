package com.awecode.thupraiisbnscanner.model

import com.awecode.thupraiisbnscanner.utils.Constants
import com.chibatching.kotpref.KotprefModel

object Setting : KotprefModel() {
    var delayTime by longPref(default = Constants.DEFAULT_SCAN_DELAY)
    var priceInputStatus by booleanPref(default = true)
}

data class Currency(var name: String? = null,
                    var code: String? = null,
                    var checked: Boolean? = null)