package com.example.practica09_almacenamientoconsqlite.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.practica09_almacenamientoconsqlite.R
import com.example.practica09_almacenamientoconsqlite.models.Park
import com.example.practica09_almacenamientoconsqlite.models.Ranger

class RangersRecyclerAdapter(rangers: MutableList<Ranger>) :
    RecyclerView.Adapter<RangersRecyclerAdapter.ViewHolder>() {

    var Rangers: MutableList<Ranger> = rangers

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rangerName = view.findViewById<TextView>(R.id.ranger_name_text_view)
        val rangerAge = view.findViewById<TextView>(R.id.ranger_age_text_view)
        val rangerTenure = view.findViewById<TextView>(R.id.ranger_tenure_text_view)
        val rangerGender = view.findViewById<TextView>(R.id.ranger_gender_text_view)
        fun bind(ranger: Ranger) {
            with(ranger) {
                rangerName.setText(name)
                rangerAge.setText(age.toString())
                rangerTenure.setText(tenure.toString())
                rangerGender.setText(gender)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.ranger_list_element, parent, false))
    }

    override fun getItemCount(): Int {
        return Rangers.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = Rangers.get(position)
        holder.bind(item)
    }
}