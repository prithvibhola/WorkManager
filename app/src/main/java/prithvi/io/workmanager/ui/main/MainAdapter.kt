package prithvi.io.workmanager.ui.main

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.holder_location.view.*
import prithvi.io.workmanager.R
import prithvi.io.workmanager.data.persistence.Location
import prithvi.io.workmanager.ui.base.BaseViewHolder

class MainAdapter(
        val context: Context,
        locations: List<Location>
) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    var locations: List<Location> = locations
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.holder_location, parent, false))

    override fun getItemCount(): Int = locations.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()

    inner class ViewHolder(itemView: View) : BaseViewHolder(itemView) {

        override fun bind() {

            val location = locations[adapterPosition]

            itemView.apply {
                tvId.text = location.id.toString()
                tvLatitude.text = location.latitude.toString()
                tvLongitude.text = location.longitude.toString()
                tvTime.text = location.timestamp.toString()
            }
        }
    }
}