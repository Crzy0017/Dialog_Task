package com.example.currencyi.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyi.R
import com.example.currencyi.model.Add
import com.example.currencyi.model.Currency


private val newCurrency = Currency("Тенге, Казахстан", 500000, 1, R.drawable.kz)
class AddViewHolder (
    inflater: LayoutInflater,
    parent: ViewGroup,
    private val addNewItem: (Currency) -> Unit,
    private val scroll: () -> Unit
): RecyclerView.ViewHolder(inflater.inflate(R.layout.add_item, parent, false)) {

    private val addView = itemView.findViewById<View>(R.id.add_view)

    fun onBind() {
        addView.setOnClickListener {
            addNewItem(newCurrency)
            scroll()
        }
    }
}