package com.example.currencyi.itemtouchhelper

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyi.ui.Adapter

class SwipeRight(private val adapter: Adapter?) : ItemTouchHelper.SimpleCallback(
    0, ItemTouchHelper.RIGHT) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        adapter?.deleteItem(viewHolder.adapterPosition)
    }

}