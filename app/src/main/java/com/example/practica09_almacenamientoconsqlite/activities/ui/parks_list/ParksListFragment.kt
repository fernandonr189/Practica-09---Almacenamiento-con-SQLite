package com.example.practica09_almacenamientoconsqlite.activities.ui.parks_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practica09_almacenamientoconsqlite.adapters.ParksRecyclerAdapter
import com.example.practica09_almacenamientoconsqlite.databinding.FragmentParkListBinding
import com.example.practica09_almacenamientoconsqlite.models.Park

class ParksListFragment : Fragment() {

    private var _binding: FragmentParkListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private var parkList: MutableList<Park> = ArrayList()
    private var recyclerAdapter: ParksRecyclerAdapter = ParksRecyclerAdapter(parkList)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val parksListViewModel =
            ViewModelProvider(this)[ParksListViewModel::class.java]

        parkList.add(Park("Colomos", 1, 123.0, 133.0, 123.0))
        parkList.add(Park("Colomos 2", 1, 123.0, 133.0, 123.0))
        parkList.add(Park("Colomos 3", 1, 123.0, 133.0, 123.0))
        parkList.add(Park("Colomos 4", 1, 123.0, 133.0, 123.0))
        parkList.add(Park("Colomos 5", 1, 123.0, 133.0, 123.0))

        _binding = FragmentParkListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        recyclerView = binding.parksListRecycler
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity)

        parksListViewModel.text.observe(viewLifecycleOwner) {
            //textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}