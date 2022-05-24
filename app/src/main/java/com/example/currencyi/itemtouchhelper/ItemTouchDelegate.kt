package com.example.currencyi.itemtouchhelper

import androidx.recyclerview.widget.RecyclerView

interface ItemTouchDelegate {
    fun startDragging(viewHolder: RecyclerView.ViewHolder)
}