package ru.akumakeito.presentation.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.akumakeito.effectivemobile_test.R
import ru.akumakeito.effectivemobile_test.databinding.FragmentProfileBinding
import ru.akumakeito.presentation.viewmodel.ProductViewModel
import ru.akumakeito.presentation.viewmodel.UserViewModel
import ru.akumakeito.util.Constants
import ru.akumakeito.util.StringUtil.Companion.formatPhoneNumber
import ru.akumakeito.util.StringUtil.Companion.getItemCountString


@AndroidEntryPoint
class FragmentProfile : Fragment() {

    val productViewModel: ProductViewModel by viewModels()
    val userViewModel: UserViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentProfileBinding.inflate(inflater, container, false)

        var favoriteItemCount: Int

        productViewModel.favoriteProducts.observe(viewLifecycleOwner) {
            favoriteItemCount = it.size

            binding.tvItemNumber.apply {

                if (favoriteItemCount == 0) {
                    binding.tvItemNumber.visibility = View.GONE
                }
                text = requireContext().getString(
                    R.string.item_number,
                    favoriteItemCount,
                    getItemCountString(favoriteItemCount)
                )
            }

        }



        binding.btnFavorite.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentProfile_to_fragmentFavorite)
        }

        userViewModel.userData.observe(viewLifecycleOwner) {

            Log.d("phonenum", formatPhoneNumber(it[stringPreferencesKey(Constants.USER_PHONE)] ?: ""))
            binding.tvUserName.text = requireContext().getString(
                R.string.name_surname,
                it[stringPreferencesKey(Constants.USER_NAME)],
                it[stringPreferencesKey(Constants.USER_SURNAME)]
            )
            binding.tvPhoneNumber.text = formatPhoneNumber(it[stringPreferencesKey(Constants.USER_PHONE)] ?: "")
        }

        binding.btnExit.setOnClickListener {
            userViewModel.exit()
        }

        return binding.root
    }
}