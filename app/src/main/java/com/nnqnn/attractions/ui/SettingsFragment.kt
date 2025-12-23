package com.nnqnn.attractions.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.switchmaterial.SwitchMaterial
import androidx.fragment.app.Fragment
import com.nnqnn.attractions.R
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SettingsFragment : Fragment() {

    private val viewModel: AttractionsViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_settings, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val switchTheme: SwitchMaterial = view.findViewById(R.id.switchTheme)
        switchTheme.isChecked = viewModel.isDark()
        switchTheme.setOnCheckedChangeListener { _, _ -> viewModel.toggleTheme() }
    }
}

