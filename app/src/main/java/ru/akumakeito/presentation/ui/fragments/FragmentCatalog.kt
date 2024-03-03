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
import ru.akumakeito.effectivemobile_test.domain.model.Tags
import ru.akumakeito.presentation.ui.adapters.CardItemAdapter
import ru.akumakeito.presentation.ui.adapters.OnCardInteractionListener
import ru.akumakeito.presentation.viewmodel.ProductViewModel

@AndroidEntryPoint
class FragmentCatalog : Fragment() {

    val productViewModel: ProductViewModel by viewModels()
    private lateinit var binding: FragmentCatalogBinding
    private val sortingList: MutableList<String> = mutableListOf()
    private lateinit var arrayAdapter: ArrayAdapter<String>
    private var lastSelectedChip: Chip? = null
    private var defaultCheckedChipId: Int = 0
    private lateinit var cardAdapter: CardItemAdapter
    private lateinit var chipGroup: ChipGroup


    companion object {
        @ApplicationContext
        lateinit var context: Context
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

        cardAdapter = CardItemAdapter(requireContext(), object : OnCardInteractionListener {
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

        binding.apply {

            chipGroup = chipgroup

            sortingAutocompleteTv.setAdapter(arrayAdapter)

            cardList.adapter = cardAdapter
            cardList.itemAnimator = null

            return binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            sortingAutocompleteTv.setOnItemClickListener { parent, _, position, _ ->
                val selectedItem = SortType.entries.find {
                    it.value == parent.getItemAtPosition(position).toString()
                } ?: SortType.POPULARITY_ASC

                productViewModel.setSortType(selectedItem)
                cardList.smoothScrollToPosition(0)
            }
            productViewModel.tags.forEach {
                chipgroup.addView(createChip(it))
            }

            chipgroup.setOnCheckedStateChangeListener { chipgroup, checkedId ->
                cardList.smoothScrollToPosition(0)
                if (checkedId.isEmpty()) {

                    productViewModel.setFilter(Tags.notag)


//                    productViewModel.applyFilters(Tags.notag)
                } else {

                    val chip = chipgroup.findViewById<Chip>(checkedId.first())

                    Log.d("chip", "chip ${chip.text}")
                    Log.d("chip", "lastSelectedChip ${lastSelectedChip?.text}")
                    val tag = Tags.entries.find { it.tagName == chip.text } ?: Tags.notag
                    chip.apply {
                        isCloseIconVisible = isChecked

                        setOnCloseIconClickListener {
                            if (tag != Tags.notag) {

                                chipGroup.clearCheck()
                                isCloseIconVisible = chip.isChecked
                                productViewModel.resetFilters()
                                chipGroup.check(defaultCheckedChipId)

                            }
                        }

                    }

                    productViewModel.setFilter(tag)

//                    productViewModel.applyFilters(tag)
                    Log.d("chip", "${chip.text} ${chip.isChecked}")


                }

            }


            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    launch {
                        productViewModel.products.collectLatest {
                            cardAdapter.submitList(it)
                        }
                    }


//                    launch {
//                        productViewModel.sortedProducts.collectLatest {
//                            cardAdapter.submitList(it)
//                        }
//                    }
//
//                    launch {
//                        productViewModel.filteredProducts.collectLatest {
//                            cardAdapter.submitList(it)
//                        }
//                    }
                }
            }
        }
    }


    override fun onResume() {
        super.onResume()

        binding.sortingAutocompleteTv.setAdapter(arrayAdapter)
    }


    private fun createChip(label: String): Chip {
        val chip = Chip(requireContext(), null)
        chip.apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            text = label

            id = View.generateViewId()



            if (label == Tags.notag.tagName) {
                isChecked = true
                lastSelectedChip = this

                setOnClickListener { }
            } else {
                isChecked = false
            }

            isCloseIconVisible = chip.isChecked
            isCheckable = true
            isClickable = true

            val clickListener = View.OnClickListener { view ->
                if (label == Tags.notag.tagName) {
                    return@OnClickListener
                }

                if (lastSelectedChip != view) {
                    lastSelectedChip?.isCloseIconVisible = false
                    lastSelectedChip = view as Chip
                }
            }

            setOnClickListener(clickListener)


        }

        return chip
    }
}

