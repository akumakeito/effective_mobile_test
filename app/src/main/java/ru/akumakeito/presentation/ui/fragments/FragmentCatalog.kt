package ru.akumakeito.presentation.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import ru.akumakeito.effectivemobile_test.R
import ru.akumakeito.effectivemobile_test.databinding.FragmentCatalogBinding
import ru.akumakeito.effectivemobile_test.domain.model.Product
import ru.akumakeito.presentation.ui.adapters.CardItemAdapter
import ru.akumakeito.presentation.ui.adapters.OnCardInteractionListener
import ru.akumakeito.presentation.viewmodel.ProductViewModel

@AndroidEntryPoint
class FragmentCatalog : Fragment() {

    val productViewModel: ProductViewModel by viewModels()
    private lateinit var binding: FragmentCatalogBinding
    private var touchX = 0
    private var touchY = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCatalogBinding.inflate(inflater, container, false)

        val adapter = CardItemAdapter(requireContext(), object : OnCardInteractionListener {
            override fun onCardClick(product: Product) {
                val bundle = Bundle()
                bundle.putString("PRODUCT_ID_KEY", product.id)
                findNavController().navigate(
                    R.id.action_fragmentCatalog_to_fragmentAboutItem,
                    bundle
                )
            }

            override fun onFavoriteClick(product: Product) {
                productViewModel.updateFavoriteProduct(product)
            }
        })


        binding.btnSortBy.setOnClickListener {
            touchX = it.x.toInt()
            touchY = it.y.toInt()

            val inflaterPopup =
                requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            val popupView = inflaterPopup.inflate(R.layout.layout_sort_menu, null)

            val popupWindow = PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
            )

            popupWindow.showAsDropDown(it, touchX, touchY)

            popupView.findViewById<TextView>(R.id.sort_by_popular).setOnClickListener {
                Log.d("sorted","клик по популярности")
                productViewModel.sortBy(requireContext().getString(R.string.by_popular))
                popupWindow.dismiss()
            }

            popupView.findViewById<TextView>(R.id.sort_by_price_decrease).setOnClickListener {
                Log.d("sorted","клик по уменьшению")
                productViewModel.sortBy(requireContext().getString(R.string.by_price_decrease))
                popupWindow.dismiss()
            }

            popupView.findViewById<TextView>(R.id.sort_price_increase).setOnClickListener {
                Log.d("sorted","клик по возрастанию")
                productViewModel.sortBy(requireContext().getString(R.string.by_price_increase))
                popupWindow.dismiss()
            }

        }

//         setupChipGroupDynamically(tagList)

        binding.cardList.adapter = adapter
        binding.cardList.itemAnimator = null


        binding.chipgroup.setOnCheckedStateChangeListener{ chipgroup, checkedId ->

//            val chip = chipgroup.findViewById<Chip>(checkedId)
//            if (chip != null) {
//                productViewModel.getProductsByTag(chip.text.toString())
//            }

        }




        productViewModel.products.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val sortingList = resources.getStringArray(R.array.sorting)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.sorting_menu_item, sortingList)
        binding.sortingAutocompleteTv.setAdapter(arrayAdapter)
    }

    private fun setupChipGroupDynamically(list: List<String>) {
        val chipGroup = ChipGroup(requireContext())
        chipGroup.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        chipGroup.isSingleSelection = true
        chipGroup.isSingleLine = true


        list.forEach {
            chipGroup.addView(createChip(it))

            Log.d("chipGroup", "add chip ${it}")
        }

        binding.chipContaner.addView(chipGroup)
    }

    private fun createChip(label: String): Chip {
        val chip = Chip(requireContext(), null, R.style.ChipSuggestion)
        chip.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        chip.text = label
        if (chip.isChecked) {
            chip.isCloseIconVisible = true
        }
        chip.isChipIconVisible = false
        chip.isCheckable = true
        chip.isClickable = true

        chip.setOnCloseIconClickListener {
            chip.isChecked = false
            productViewModel.getProducts()
        }
        return chip
    }
}

