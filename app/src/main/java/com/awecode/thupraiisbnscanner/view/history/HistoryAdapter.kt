package com.awecode.thupraiisbnscanner.view.history

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

                itemView.isbnTextView.text = "ISBN: $isbn"
                itemView.dateTextView.text = date

                if (price.isNullOrEmpty())
                    itemView.priceTextView.visibility = View.GONE
                else {
                    itemView.priceTextView.visibility = View.VISIBLE
                    itemView.priceTextView.text = "Price: Rs $price"
                }


                if (image != null) {
                    //show saved barcode image
                    val bitmap = BitmapFactory.decodeByteArray(image, 0, image?.size!!)
                    itemView.barcodeImageView.setImageBitmap(bitmap)
                } else
                    itemView.barcodeImageView.setImageBitmap(null)

            }
        }
    }
}