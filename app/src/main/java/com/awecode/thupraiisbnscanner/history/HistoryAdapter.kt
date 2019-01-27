package com.awecode.thupraiisbnscanner.history

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.awecode.thupraiisbnscanner.R
import com.awecode.thupraiisbnscanner.db.entity.BarcodeData
import kotlinx.android.synthetic.main.barcode_item.view.*

class HistoryAdapter(val dataList: List<BarcodeData>, val itemClick: (BarcodeData) -> Unit) :
        RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.barcode_item, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindHistory(dataList[position])
    }

    override fun getItemCount() = dataList.size


    class ViewHolder(view: View, val itemClick: (BarcodeData) -> Unit) : RecyclerView.ViewHolder(view) {

        fun bindHistory(data: BarcodeData) {
            with(data) {

                itemView.isbnTextView.text = isbn
                itemView.dateTextView.text = date

                //show saved barcode image
                val bitmap = BitmapFactory.decodeByteArray(data.image, 0, image?.size!!)
                itemView.barcodeImageView.setImageBitmap(bitmap)
            }
        }
    }
}