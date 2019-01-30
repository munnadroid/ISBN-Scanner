package com.awecode.thupraiisbnscanner.utils

import java.text.SimpleDateFormat
import java.util.*
import android.content.Context
import java.io.*
import android.content.Intent
import android.net.Uri
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import java.net.URLConnection


class CommonUtils {
    companion object {


        fun showKeyboard(context: Context) {
            val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }

        fun showKeyboard(context: Context, editText: EditText) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm!!.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
        }

        /**
         * Share file via intent
         */
        fun shareFile(context: Context, file: File) {

            val intentShareFile = Intent(Intent.ACTION_SEND)

            intentShareFile.type = URLConnection.guessContentTypeFromName(file.name)
            intentShareFile.putExtra(Intent.EXTRA_STREAM,
                    Uri.parse("file://" + file.absolutePath))
            context.startActivity(Intent.createChooser(intentShareFile, "Share File"))

        }

        /**
         * Get Now time for xls file name
         * Format: MM-dd-yyyy-HH:mm:ss
         */
        fun getNowDateForFileName(): String {
            // Create an instance of SimpleDateFormat used for formatting
            // the string representation of date (month/day/year)
            val df = SimpleDateFormat("MM-dd-yyyy-HH:mm:ss")

            // Get the date today using Calendar object.
            val today = Calendar.getInstance().time
            // Using DateFormat format method we can create a string
            // representation of a date with the defined format.
            return df.format(today)
        }

        /**
         * Get Now time for db date
         * Format: MM/dd/yyyy HH:mm:ss
         */
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


        /**
         * Get byte array from filePath path
         */
        @Throws(IOException::class)
        fun readFile(filePath: String): ByteArray {
            return readFile(File(filePath))
        }

        @Throws(IOException::class)
        private fun readFile(file: File): ByteArray {
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