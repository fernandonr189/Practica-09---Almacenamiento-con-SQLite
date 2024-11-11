package com.example.practica09_almacenamientoconsqlite.activities.ui.ranger_list

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.practica09_almacenamientoconsqlite.R

class RangerListFragment : Fragment() {

    companion object {
        fun newInstance() = RangerListFragment()
    }

    private val viewModel: RangerListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_ranger_list, container, false)
    }
}