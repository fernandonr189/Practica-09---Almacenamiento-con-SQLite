package com.example.practica09_almacenamientoconsqlite.activities.ui.ranger_form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.practica09_almacenamientoconsqlite.databinding.FragmentRangerFormBinding
import com.google.android.material.textfield.TextInputEditText

class RangerFormFragment : Fragment() {

    private var _binding: FragmentRangerFormBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var rangerNameInput: TextInputEditText
    private lateinit var rangerAgeInput: TextInputEditText
    private lateinit var rangerTenureInput: TextInputEditText
    private lateinit var rangerGenderMaleInput: CheckBox
    private lateinit var rangerGenderFemaleCheckBox: CheckBox
    private lateinit var registerRangerButton: Button
    private lateinit var findRangerButton: Button
    private lateinit var editRangerButton: Button
    private lateinit var deleteRangerButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rangerFormViewModel =
            ViewModelProvider(this).get(RangerFormViewModel::class.java)

        _binding = FragmentRangerFormBinding.inflate(inflater, container, false)
        val root: View = binding.root

        rangerNameInput = binding.rangerNameInput
        rangerAgeInput = binding.rangerAgeInput
        rangerTenureInput = binding.rangerTenureInput
        rangerGenderMaleInput = binding.maleGenderCheckbox
        rangerGenderFemaleCheckBox = binding.femaleGenderCheckbox
        registerRangerButton = binding.registerRangerButton
        findRangerButton = binding.findRangerButton
        editRangerButton = binding.editRangerButton
        deleteRangerButton = binding.deleteRangerButton

        //val textView: TextView = binding.textSlideshow
        rangerFormViewModel.text.observe(viewLifecycleOwner) {
            //textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}