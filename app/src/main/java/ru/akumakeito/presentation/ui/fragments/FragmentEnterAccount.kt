package ru.akumakeito.presentation.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import ru.akumakeito.util.StringUtil

@AndroidEntryPoint
class FragmentEnterAccount : Fragment() {

    private val userViewModel: UserViewModel by viewModels()
    private lateinit var binding : FragmentEnterAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEnterAccountBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            eTName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    val text = s.toString()
                    if (!StringUtil.isOnlyRussianLetters(text)) {
                        eTName.setError(getString(R.string.enter_name_on_rus))

                    } else {
                        userViewModel.setUser(name = text)
                    }
                }

            })

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
    }
}