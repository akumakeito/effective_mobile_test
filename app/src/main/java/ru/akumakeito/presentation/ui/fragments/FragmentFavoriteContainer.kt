package ru.akumakeito.presentation.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.akumakeito.effectivemobile_test.R
import ru.akumakeito.effectivemobile_test.databinding.FragmentFavoriteContainerBinding
import ru.akumakeito.effectivemobile_test.domain.model.Product
import ru.akumakeito.presentation.ui.adapters.CardItemAdapter
import ru.akumakeito.presentation.ui.adapters.OnCardInteractionListener
import ru.akumakeito.presentation.viewmodel.ProductViewModel


@AndroidEntryPoint
class FragmentFavoriteContainer : Fragment() {

    private val productViewModel: ProductViewModel by viewModels()
    private lateinit var adapter: CardItemAdapter
    private lateinit var binding: FragmentFavoriteContainerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentFavoriteContainerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        adapter = CardItemAdapter(requireContext(), object : OnCardInteractionListener {
            override fun onCardClick(product: Product) {
                val bundle = Bundle()
                bundle.putString("PRODUCT_ID_KEY", product.id)
                findNavController().navigate(
                    R.id.fragmentAboutItem,
                    bundle
                )

            }

            override fun onFavoriteClick(product: Product) {
                productViewModel.updateFavoriteProduct(product)
            }
        })

        productViewModel.favoriteProducts.observe(viewLifecycleOwner) {
            Log.d("favorite", "${it}")
            adapter.submitList(it)
        }



        binding.favoriteList.adapter = adapter
    }
}