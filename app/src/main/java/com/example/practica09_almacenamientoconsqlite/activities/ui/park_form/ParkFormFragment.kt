package com.example.practica09_almacenamientoconsqlite.activities.ui.park_form

import android.content.ContentValues
import android.os.Bundle
import android.provider.BaseColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.practica09_almacenamientoconsqlite.databinding.FragmentParkFormBinding
import com.example.practica09_almacenamientoconsqlite.models.Park
import com.example.practica09_almacenamientoconsqlite.sql.ParkContract
import com.example.practica09_almacenamientoconsqlite.sql.ParksDbHelper
import com.google.android.material.textfield.TextInputEditText

class ParkFormFragment : Fragment() {

    private var _binding: FragmentParkFormBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var textViewTitle: TextView
    private lateinit var parkNameInput: TextInputEditText
    private lateinit var parkSizeInput: TextInputEditText
    private lateinit var parkLongitudeInput: TextInputEditText
    private lateinit var parkLatitudeInput: TextInputEditText
    private lateinit var registerParkButton: Button
    private lateinit var findParkButton: Button
    private lateinit var editParkButton: Button
    private lateinit var deleteParkButton: Button
    private lateinit var dbHelper: ParksDbHelper


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val parkFormViewModel =
            ViewModelProvider(this)[ParkFormViewModel::class.java]

        _binding = FragmentParkFormBinding.inflate(inflater, container, false)
        val root: View = binding.root

        dbHelper = ParksDbHelper(requireActivity().baseContext)

        textViewTitle = binding.parkFormTitle;
        parkNameInput = binding.parkNameInput
        parkSizeInput = binding.parkSizeInput
        parkLongitudeInput = binding.parkLocationLongitudeInput
        parkLatitudeInput = binding.parkLocationLatitudeInput
        registerParkButton = binding.registerParkButton
        findParkButton = binding.findParkButton
        editParkButton = binding.editParkButton
        deleteParkButton = binding.deleteParkButton

        deleteParkButton.setOnClickListener {
            deleteParkFromDb()
        }
        
        registerParkButton.setOnClickListener { 
            if(validateForm()) {
                addParkToDb()
            }
            else {
                Toast.makeText(requireActivity(), "Por favor completa el formulario", Toast.LENGTH_SHORT).show()
            }
        }

        findParkButton.setOnClickListener {
            if(!parkNameInput.text.isNullOrEmpty()) {
                findParkFromDb(true)
            }
            else {
                Toast.makeText(activity, "Por favor ingrese un nombre para buscar", Toast.LENGTH_SHORT).show()
            }
        }

        parkFormViewModel.text.observe(viewLifecycleOwner) {
            //textView.text = it
        }
        return root
    }

    private fun clearForm() {
        parkNameInput.setText("")
        parkLatitudeInput.setText("")
        parkLongitudeInput.setText("")
        parkSizeInput.setText("")
    }

    private fun deleteParkFromDb() {
        val parkId = findParkFromDb(false)
        if(parkId != -1) {
            val db = dbHelper.writableDatabase
            val selection = "${BaseColumns._ID} LIKE ?"
            val selectionArgs = arrayOf(parkId.toString())
            db.delete(ParkContract.ParkEntry.TABLE_NAME, selection, selectionArgs)
            clearForm()
            Toast.makeText(activity, "Parque eliminado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateForm() : Boolean {
        return !(parkNameInput.text.isNullOrEmpty() ||
                parkSizeInput.text.isNullOrEmpty() ||
                parkLatitudeInput.text.isNullOrEmpty() ||
                parkLongitudeInput.text.isNullOrEmpty())
    }

    private fun findParkFromDb(printInForm: Boolean) : Int {
        val db = dbHelper.readableDatabase

        val projection = arrayOf(BaseColumns._ID,
            ParkContract.ParkEntry.COLUMN_NAME_NAME,
            ParkContract.ParkEntry.COLUMN_NAME_LONGITUDE,
            ParkContract.ParkEntry.COLUMN_NAME_LATITUDE,
            ParkContract.ParkEntry.COLUMN_NAME_SIZE)
        val selection = "${ParkContract.ParkEntry.COLUMN_NAME_NAME} = ?"
        val selectionArgs = arrayOf(parkNameInput.text.toString())
        val sortOrder = "${ParkContract.ParkEntry.COLUMN_NAME_NAME} DESC"

        val cursor = db.query(
            ParkContract.ParkEntry.TABLE_NAME,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            selection,              // The columns for the WHERE clause
            selectionArgs,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            sortOrder               // The sort order
        )
        val parks = mutableListOf<Park>()
        with(cursor) {
            while (moveToNext()) {
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
        if(parks.size > 0) {
            val park = parks[0]
            if(printInForm) {
                parkNameInput.setText(park.parkName)
                parkSizeInput.setText(park.size.toString())
                parkLatitudeInput.setText(park.latitude.toString())
                parkLongitudeInput.setText(park.longitude.toString())
            }
            return park.id
        }
        else {
            Toast.makeText(activity, "Parque no encontrado", Toast.LENGTH_SHORT).show()
        }
        return -1
    }

    private fun addParkToDb() {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(ParkContract.ParkEntry.COLUMN_NAME_NAME, parkNameInput.text.toString())
            put(ParkContract.ParkEntry.COLUMN_NAME_SIZE, parkSizeInput.text.toString().toDouble())
            put(ParkContract.ParkEntry.COLUMN_NAME_LONGITUDE, parkLongitudeInput.text.toString().toDouble())
            put(ParkContract.ParkEntry.COLUMN_NAME_LATITUDE, parkLatitudeInput.text.toString().toDouble())
        }
        db.insert(ParkContract.ParkEntry.TABLE_NAME, null, values)
    }

    override fun onDestroyView() {
        dbHelper.close()
        super.onDestroyView()
        _binding = null
    }
}