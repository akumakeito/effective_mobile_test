package ru.akumakeito.presentation.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.akumakeito.effectivemobile_test.R
import ru.akumakeito.effectivemobile_test.databinding.FragmentCatalogBinding
import ru.akumakeito.effectivemobile_test.domain.model.Product
import ru.akumakeito.effectivemobile_test.domain.model.SortType
import ru.akumakeito.presentation.ui.adapters.CardItemAdapter
import ru.akumakeito.presentation.ui.adapters.OnCardInteractionListener
import ru.akumakeito.presentation.viewmodel.ProductViewModel

@AndroidEntryPoint
class FragmentCatalog : Fragment() {

    val productViewModel: ProductViewModel by viewModels()
    private lateinit var binding: FragmentCatalogBinding
    private val sortingList : MutableList<String> = mutableListOf()
    private lateinit var arrayAdapter: ArrayAdapter<String>

    companion object {
        @ApplicationContext
        lateinit var context : Context
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FragmentCatalog.context = context
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SortType.entries.forEach {
            sortingList.add(it.value)
        }
        arrayAdapter = ArrayAdapter(requireContext(), R.layout.sorting_menu_item, sortingList)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCatalogBinding.inflate(inflater, container, false)

        binding.sortingAutocompleteTv.setAdapter(arrayAdapter)


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


        binding.sortingAutocompleteTv.setOnItemClickListener { parent, _, position, _ ->
            val selectedItem = SortType.entries.find {it.value == parent.getItemAtPosition(position).toString() } ?: SortType.POPULARITY_ASC

            productViewModel.sortBy(selectedItem)

        }

//         setupChipGroupDynamically(tagList)

        binding.cardList.adapter = adapter
        binding.cardList.itemAnimator = null


        binding.chipgroup.setOnCheckedStateChangeListener { chipgroup, checkedId ->

//            val chip = chipgroup.findViewById<Chip>(checkedId)
//            if (chip != null) {
//                productViewModel.getProductsByTag(chip.text.toString())
//            }

        }


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    productViewModel.products.collectLatest {
                        adapter.submitList(it)
                    }
                }

                launch {
                    productViewModel.sortedProducts.collectLatest {
                        adapter.submitList(it)
                    }
                }



            }
        }


//        lifecycleScope.launch {
//            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
//
//                    productViewModel.uiState.collectLatest { state ->
//                        state.loading.let {
//                            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
//                        }
//
//                        state.content.let {
//                            adapter.submitList(it)
//                        }
//
//                }
//            }
//        }

    return binding.root
}


override fun onResume() {
    super.onResume()

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

