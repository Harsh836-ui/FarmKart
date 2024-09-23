package com.example.farmkat.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

//job is to add some extra spaces between our rv items
class HorizontalItemDecoration(private val amount : Int = 15):RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.right = amount
    }
}