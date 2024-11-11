package com.example.practica09_almacenamientoconsqlite.activities.ui.parks_list

import android.os.Bundle
import android.provider.BaseColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practica09_almacenamientoconsqlite.adapters.ParksRecyclerAdapter
import com.example.practica09_almacenamientoconsqlite.databinding.FragmentParkListBinding
import com.example.practica09_almacenamientoconsqlite.models.Park
import com.example.practica09_almacenamientoconsqlite.sql.ParkContract
import com.example.practica09_almacenamientoconsqlite.sql.ParksDbHelper

class ParksListFragment : Fragment() {

    private var _binding: FragmentParkListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private var parkList: MutableList<Park> = ArrayList()
    private lateinit var recyclerAdapter: ParksRecyclerAdapter
    private lateinit var dbHelper: ParksDbHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val parksListViewModel =
            ViewModelProvider(this)[ParksListViewModel::class.java]
        dbHelper = ParksDbHelper(requireActivity().baseContext)


        parkList = getParksFromDb()
        recyclerAdapter = ParksRecyclerAdapter(parkList)

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

    private fun getParksFromDb() : MutableList<Park>{
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM ${ParkContract.ParkEntry.TABLE_NAME}", null)
        val parks = mutableListOf<Park>()
        with(cursor) {
            while (moveToNext()) {
                println("found")
                val foundPark = Park(
                    getString(getColumnIndexOrThrow(ParkContract.ParkEntry.COLUMN_NAME_NAME)),
                    getInt(getColumnIndexOrThrow(BaseColumns._ID)),
                    getDouble(getColumnIndexOrThrow(ParkContract.ParkEntry.COLUMN_NAME_LONGITUDE)),
                    getDouble(getColumnIndexOrThrow(ParkContract.ParkEntry.COLUMN_NAME_LATITUDE)),
                    getDouble(getColumnIndexOrThrow(ParkContract.ParkEntry.COLUMN_NAME_SIZE))
                )
                parks.add(foundPark)
            }
        }
        cursor.close()
        return parks
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}