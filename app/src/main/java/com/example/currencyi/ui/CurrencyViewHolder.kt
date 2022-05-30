package com.example.currencyi.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyi.R
import com.example.currencyi.model.Currency
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class CurrencyViewHolder (inflater: LayoutInflater, parent: ViewGroup, val changedToolbar: (Currency) -> Unit) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_currency, parent, false)) {

    private val currencyText = itemView.findViewById<TextInputLayout>(R.id.currencyText)
    private val label = itemView.findViewById<TextView>(R.id.Label)
    private val img = itemView.findViewById<ImageView>(R.id.Image)
    fun onBind(item: Currency) {
        currencyText.editText?.setText(item.value.toString())
        currencyText.hint = item.textview
        label.text = item.textview
        img.setBackgroundResource(item.imageRes)
        img.setOnLongClickListener {
            changedToolbar(item)
            return@setOnLongClickListener true
        }
    }
}