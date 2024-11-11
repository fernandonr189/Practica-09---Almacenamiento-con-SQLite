package com.example.practica09_almacenamientoconsqlite.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.practica09_almacenamientoconsqlite.R
import com.example.practica09_almacenamientoconsqlite.models.park

class ParksRecyclerAdapter(park: MutableList<park>) :
    RecyclerView.Adapter<ParksRecyclerAdapter.ViewHolder>() {

    var parks: MutableList<park> = park

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val parkName = view.findViewById<TextView>(R.id.park_name_text_view)
        val parkLatitude = view.findViewById<TextView>(R.id.park_latitude_text_view)
        val parkLongitude = view.findViewById<TextView>(R.id.park_longitude_text_view)
        var parkSize = view.findViewById<TextView>(R.id.park_size_text_view)
        fun bind(park: park) {
            parkName.setText(park.parkName)
            parkLatitude.setText("Latitud: ${park.latitude}")
            parkLongitude.setText("Longitud: ${park.longitude}")
            parkSize.setText("Tamaño: ${park.size}")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.park_list_element, parent, false))
    }

    override fun getItemCount(): Int {
        return parks.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = parks.get(position)
        holder.bind(item)
    }
}