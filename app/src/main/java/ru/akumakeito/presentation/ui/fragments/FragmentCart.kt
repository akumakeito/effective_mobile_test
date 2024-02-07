package ru.akumakeito.presentation.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import ru.akumakeito.effectivemobile_test.databinding.FragmentCartBinding

@AndroidEntryPoint
class FragmentCart :Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCartBinding.inflate(inflater, container, false)
        Log.d("navbarproblem", "fragmentcart")
        return binding.root
    }
}