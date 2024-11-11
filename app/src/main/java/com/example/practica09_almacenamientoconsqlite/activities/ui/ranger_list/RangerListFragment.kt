package com.example.practica09_almacenamientoconsqlite.activities.ui.ranger_list

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practica09_almacenamientoconsqlite.R
import com.example.practica09_almacenamientoconsqlite.adapters.RangersRecyclerAdapter
import com.example.practica09_almacenamientoconsqlite.databinding.FragmentParkListBinding
import com.example.practica09_almacenamientoconsqlite.databinding.FragmentRangerListBinding
import com.example.practica09_almacenamientoconsqlite.models.Ranger

class RangerListFragment : Fragment() {

    private var _binding: FragmentRangerListBinding? = null

    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private var rangerList: MutableList<Ranger> = ArrayList()
    private lateinit var recyclerAdapter: RangersRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRangerListBinding.inflate(inflater, container, false)
        val root = binding.root

        rangerList.add(Ranger("Juan", 1, 24, "Hombre", 23))
        rangerList.add(Ranger("Juan", 1, 24, "Hombre", 23))
        rangerList.add(Ranger("Juan", 1, 24, "Hombre", 23))
        rangerList.add(Ranger("Juan", 1, 24, "Hombre", 23))

        recyclerAdapter = RangersRecyclerAdapter(rangerList)

        recyclerView = binding.rangerRecyclerView
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        return root
    }
}