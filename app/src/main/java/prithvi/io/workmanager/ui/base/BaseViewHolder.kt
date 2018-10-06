package prithvi.io.workmanager.ui.base

import android.support.v7.widget.RecyclerView
import android.view.View

open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    open fun bind() {}
}