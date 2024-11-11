package com.example.practica09_almacenamientoconsqlite.activities.ui.ranger_form

import android.content.ContentValues
import android.os.Bundle
import android.provider.BaseColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.practica09_almacenamientoconsqlite.databinding.FragmentRangerFormBinding
import com.example.practica09_almacenamientoconsqlite.models.Ranger
import com.example.practica09_almacenamientoconsqlite.sql.ParkContract
import com.example.practica09_almacenamientoconsqlite.sql.RangerContract
import com.example.practica09_almacenamientoconsqlite.sql.RangerDbHelper
import com.google.android.material.textfield.TextInputEditText

class RangerFormFragment : Fragment() {

    private var _binding: FragmentRangerFormBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var rangerNameInput: TextInputEditText
    private lateinit var rangerAgeInput: TextInputEditText
    private lateinit var rangerTenureInput: TextInputEditText
    private lateinit var rangerGenderMaleCheckBox: CheckBox
    private lateinit var rangerGenderFemaleCheckBox: CheckBox
    private lateinit var registerRangerButton: Button
    private lateinit var findRangerButton: Button
    private lateinit var editRangerButton: Button
    private lateinit var deleteRangerButton: Button
    private lateinit var dbHelper: RangerDbHelper
    private lateinit var selectedGender: String

    private var isEditing = false
    private var editingId = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rangerFormViewModel =
            ViewModelProvider(this).get(RangerFormViewModel::class.java)

        _binding = FragmentRangerFormBinding.inflate(inflater, container, false)
        val root: View = binding.root

        dbHelper = RangerDbHelper(requireActivity().baseContext)

        rangerNameInput = binding.rangerNameInput
        rangerAgeInput = binding.rangerAgeInput
        rangerTenureInput = binding.rangerTenureInput
        rangerGenderMaleCheckBox = binding.maleGenderCheckbox
        rangerGenderFemaleCheckBox = binding.femaleGenderCheckbox
        registerRangerButton = binding.registerRangerButton
        findRangerButton = binding.findRangerButton
        editRangerButton = binding.editRangerButton
        deleteRangerButton = binding.deleteRangerButton

        //val textView: TextView = binding.textSlideshow
        rangerFormViewModel.text.observe(viewLifecycleOwner) {
            //textView.text = it
        }
        findRangerButton.setOnClickListener {
            if (!rangerNameInput.text.isNullOrEmpty()) {
                if (!isEditing) {
                    findRangerFromDb(true)
                } else {
                    Toast.makeText(
                        requireActivity(),
                        "Termine de editar antes de buscar otro guardabosques!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    activity,
                    "Por favor ingrese un nombre para buscar",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        rangerGenderMaleCheckBox.setOnClickListener {
            rangerGenderFemaleCheckBox.isChecked = false
            selectedGender = "Masculino"
        }

        rangerGenderFemaleCheckBox.setOnClickListener {
            rangerGenderMaleCheckBox.isChecked = false
            selectedGender = "Femenino"
        }

        editRangerButton.setOnClickListener {
            if (!rangerNameInput.text.isNullOrEmpty()) {
                if (isEditing && validateForm()) {
                    updatePark()
                } else {
                    val rangerId = findRangerFromDb(true)
                    if (rangerId != -1) {
                        editingId = rangerId
                        isEditing = true
                        editRangerButton.setText("Actualizar")
                    }
                }
            }
        }

        deleteRangerButton.setOnClickListener {
            if (!isEditing) {
                deleteRangerFromDb()
            } else {
                Toast.makeText(
                    requireActivity(),
                    "Termine de editar antes de eliminar!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        registerRangerButton.setOnClickListener {
            if (validateForm()) {
                if (!isEditing) {
                    addRangerToDb()
                } else {
                    Toast.makeText(
                        requireActivity(),
                        "Para evitar duplicados, termined de editar antes de agregar un nuevo guardabosques",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    requireActivity(),
                    "Por favor completa el formulario",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        return root
    }

    private fun updatePark() {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(RangerContract.RangerEntry.COLUMN_NAME_NAME, rangerNameInput.text.toString())
            put(RangerContract.RangerEntry.COLUMN_NAME_AGE, rangerAgeInput.text.toString().toInt())
            put(
                RangerContract.RangerEntry.COLUMN_NAME_TENURE,
                rangerTenureInput.text.toString().toInt()
            )
            put(RangerContract.RangerEntry.COLUMN_NAME_GENDER, selectedGender)
        }

        val selection = "${BaseColumns._ID} LIKE ?"
        val selectionArgs = arrayOf(editingId.toString())
        db.update(RangerContract.RangerEntry.TABLE_NAME, values, selection, selectionArgs)
        resetEditButton()
        clearForm()
    }

    private fun resetEditButton() {
        isEditing = false
        editingId = -1
        editRangerButton.setText("Editar")
    }

    private fun findRangerFromDb(printInForm: Boolean): Int {
        val db = dbHelper.readableDatabase

        val projection = arrayOf(
            BaseColumns._ID,
            RangerContract.RangerEntry.COLUMN_NAME_NAME,
            RangerContract.RangerEntry.COLUMN_NAME_AGE,
            RangerContract.RangerEntry.COLUMN_NAME_TENURE,
            RangerContract.RangerEntry.COLUMN_NAME_GENDER
        )
        val selection = "${RangerContract.RangerEntry.COLUMN_NAME_NAME} = ?"
        val selectionArgs = arrayOf(rangerNameInput.text.toString())
        val sortOrder = "${RangerContract.RangerEntry.COLUMN_NAME_NAME} DESC"

        val cursor = db.query(
            RangerContract.RangerEntry.TABLE_NAME,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            selection,              // The columns for the WHERE clause
            selectionArgs,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            sortOrder               // The sort order
        )
        val rangers = mutableListOf<Ranger>()
        with(cursor) {
            while (moveToNext()) {
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
        if (rangers.size > 0) {
            val ranger = rangers[0]
            if (printInForm) {
                with(ranger) {
                    rangerNameInput.setText(name)
                    rangerAgeInput.setText(age.toString())
                    rangerTenureInput.setText(tenure.toString())
                    when (gender) {
                        "Masculino" -> rangerGenderMaleCheckBox.isChecked = true
                        "Femenino" -> rangerGenderFemaleCheckBox.isChecked = true
                    }
                }
            }
            return ranger.id
        } else {
            Toast.makeText(activity, "Guardabosques no encontrado", Toast.LENGTH_SHORT).show()
        }
        return -1
    }

    private fun clearForm() {
        rangerNameInput.setText("")
        rangerAgeInput.setText("")
        rangerTenureInput.setText("")
        rangerGenderMaleCheckBox.isChecked = false
        rangerGenderFemaleCheckBox.isChecked = false
    }

    private fun deleteRangerFromDb() {
        val rangerId = findRangerFromDb(false)
        if (rangerId != -1) {
            val db = dbHelper.writableDatabase
            val selection = "${BaseColumns._ID} LIKE ?"
            val selectionArgs = arrayOf(rangerId.toString())
            db.delete(RangerContract.RangerEntry.TABLE_NAME, selection, selectionArgs)
            clearForm()
            Toast.makeText(activity, "Guardabosques eliminado", Toast.LENGTH_SHORT).show()
        }
    }


    private fun addRangerToDb() {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(RangerContract.RangerEntry.COLUMN_NAME_NAME, rangerNameInput.text.toString())
            put(RangerContract.RangerEntry.COLUMN_NAME_AGE, rangerAgeInput.text.toString().toInt())
            put(
                RangerContract.RangerEntry.COLUMN_NAME_TENURE,
                rangerTenureInput.text.toString().toInt()
            )
            put(RangerContract.RangerEntry.COLUMN_NAME_GENDER, selectedGender)
        }
        db.insert(RangerContract.RangerEntry.TABLE_NAME, null, values)
    }

    private fun validateForm(): Boolean {
        return !(rangerNameInput.text.isNullOrEmpty() ||
                rangerAgeInput.text.isNullOrEmpty() ||
                rangerTenureInput.text.isNullOrEmpty() ||
                (!rangerGenderMaleCheckBox.isChecked && !rangerGenderFemaleCheckBox.isChecked))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}