package com.awecode.thupraiisbnscanner.utils

import java.text.SimpleDateFormat
import java.util.*
import android.R.attr.path
import android.util.Log
import java.io.*


class CommonUtils {
    companion object {

        fun getTodayStringDate(): String {
            // Create an instance of SimpleDateFormat used for formatting
            // the string representation of date (month/day/year)
            val df = SimpleDateFormat("MM/dd/yyyy HH:mm:ss")

            // Get the date today using Calendar object.
            val today = Calendar.getInstance().time
            // Using DateFormat format method we can create a string
            // representation of a date with the defined format.
            return df.format(today)
        }


        @Throws(IOException::class)
        fun readFile(file: String): ByteArray {
            return readFile(File(file))
        }

        @Throws(IOException::class)
        fun readFile(file: File): ByteArray {
            // Open file
            val f = RandomAccessFile(file, "r")
            try {
                // Get and check length
                val longlength = f.length()
                val length = longlength.toInt()
                if (length.toLong() != longlength)
                    throw IOException("File size >= 2 GB")
                // Read file and return data
                val data = ByteArray(length)
                f.readFully(data)
                return data
            } finally {
                f.close()
            }
        }
    }
}