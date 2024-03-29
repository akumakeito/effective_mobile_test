package ru.akumakeito.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import ru.akumakeito.effectivemobile_test.databinding.FragmentDiscountsBinding

@AndroidEntryPoint
class FragmentDiscounts :Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDiscountsBinding.inflate(inflater, container, false)
        return binding.root
    }
}