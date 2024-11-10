package com.example.practica09_almacenamientoconsqlite.activities.ui.park_form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.practica09_almacenamientoconsqlite.databinding.FragmentParkFormBinding
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


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val parkFormViewModel =
            ViewModelProvider(this)[ParkFormViewModel::class.java]

        _binding = FragmentParkFormBinding.inflate(inflater, container, false)
        val root: View = binding.root

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
            Toast.makeText(activity, "Hello world", Toast.LENGTH_SHORT).show()
        }

        parkFormViewModel.text.observe(viewLifecycleOwner) {
            //textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}