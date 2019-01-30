package com.awecode.thupraiisbnscanner.view.gun_scanner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.awecode.thupraiisbnscanner.R
import com.awecode.thupraiisbnscanner.model.Currency
import kotlinx.android.synthetic.main.currency_item.view.*
import android.widget.CompoundButton


class CurrencyListAdapter(val dataList: List<Currency>, val itemClick: (Currency) -> Unit) :
        RecyclerView.Adapter<CurrencyListAdapter.ViewHolder>() {

    private var mCheckedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.currency_item, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currency = dataList[position]
        holder.itemView.currencyRadioButton.setOnCheckedChangeListener(null)
        holder.itemView.currencyRadioButton.isChecked = position === mCheckedPosition
        holder.itemView.currencyRadioButton.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            mCheckedPosition = position
            notifyDataSetChanged()
            currency.checked = true
            itemClick(currency)

        })

        holder.bindHistory(dataList[position])
    }

    override fun getItemCount() = dataList.size


    class ViewHolder(view: View, val itemClick: (Currency) -> Unit) : RecyclerView.ViewHolder(view) {

        fun bindHistory(data: Currency) {
            with(data) {
                itemView.currencyRadioButton.text = "$name($code)"
            }
        }
    }
}