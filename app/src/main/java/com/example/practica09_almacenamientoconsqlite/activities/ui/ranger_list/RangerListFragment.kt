package com.example.practica09_almacenamientoconsqlite.activities.ui.ranger_list

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.provider.BaseColumns
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
import com.example.practica09_almacenamientoconsqlite.models.Park
import com.example.practica09_almacenamientoconsqlite.models.Ranger
import com.example.practica09_almacenamientoconsqlite.sql.ParkContract
import com.example.practica09_almacenamientoconsqlite.sql.ParksDbHelper
import com.example.practica09_almacenamientoconsqlite.sql.RangerContract
import com.example.practica09_almacenamientoconsqlite.sql.RangerDbHelper

class RangerListFragment : Fragment() {

    private var _binding: FragmentRangerListBinding? = null

    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var rangerList: MutableList<Ranger>
    private lateinit var recyclerAdapter: RangersRecyclerAdapter
    private lateinit var dbHelper: RangerDbHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRangerListBinding.inflate(inflater, container, false)
        val root = binding.root
        dbHelper = RangerDbHelper(requireActivity().baseContext)

        rangerList = getRangersFromDb()

        recyclerAdapter = RangersRecyclerAdapter(rangerList)

        recyclerView = binding.rangerRecyclerView
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        return root
    }

    private fun getRangersFromDb() : MutableList<Ranger>{
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM ${RangerContract.RangerEntry.TABLE_NAME}", null)
        val rangers = mutableListOf<Ranger>()
        with(cursor) {
            while (moveToNext()) {
                println("found")
                val foundRanger = Ranger(
                    getString(getColumnIndexOrThrow(RangerContract.RangerEntry.COLUMN_NAME_NAME)),
                    getInt(getColumnIndexOrThrow(BaseColumns._ID)),
                    getInt(getColumnIndexOrThrow(RangerContract.RangerEntry.COLUMN_NAME_AGE)),
                    getString(getColumnIndexOrThrow(RangerContract.RangerEntry.COLUMN_NAME_GENDER)),
                    getInt(getColumnIndexOrThrow(RangerContract.RangerEntry.COLUMN_NAME_TENURE))
                )
                rangers.add(foundRanger)
            }
        }
        cursor.close()
        return rangers
    }
}