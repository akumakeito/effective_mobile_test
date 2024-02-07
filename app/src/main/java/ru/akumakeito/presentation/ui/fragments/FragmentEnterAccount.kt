package ru.akumakeito.presentation.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.akumakeito.effectivemobile_test.R
import ru.akumakeito.effectivemobile_test.databinding.FragmentEnterAccountBinding
import ru.akumakeito.presentation.viewmodel.UserViewModel

@AndroidEntryPoint
class FragmentEnterAccount : Fragment() {

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentEnterAccountBinding.inflate(inflater, container, false)

        binding.apply {

            eTName.addTextChangedListener {
                userViewModel.setUser(name = eTName.text.toString())
            }

            eTSurname.addTextChangedListener {
                userViewModel.setUser(surname = eTSurname.text.toString())
            }

            eTPhoneNumber.addTextChangedListener {
                userViewModel.setUser(phoneNumber = eTPhoneNumber.text.toString())
            }

            btnEnter.setOnClickListener {
                userViewModel.createUser(eTName.text.toString(), eTSurname.text.toString(), eTPhoneNumber.text.toString())
            }

            userViewModel.isSigned.observe(viewLifecycleOwner) {
                if (it == true) {
                    findNavController().navigate(R.id.fragmentHome)
                }
            }

            userViewModel.userRegistry.observe(viewLifecycleOwner) {
                Log.d("registrationprob", it.toString())

                btnEnter.isEnabled = !(eTName.text.toString().isBlank() || eTSurname.text.toString().isBlank() || eTPhoneNumber.text.toString().isBlank())
            }

//            eTPhoneNumber.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        }
        return binding.root
    }
}