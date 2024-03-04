package ru.akumakeito.presentation.ui.fragments

import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import ru.akumakeito.effectivemobile_test.R
import ru.akumakeito.effectivemobile_test.databinding.FragmentAboutItemBinding
import ru.akumakeito.presentation.ui.adapters.ItemImageAdapter
import ru.akumakeito.presentation.ui.adapters.OnInteractionListener
import ru.akumakeito.presentation.ui.adapters.ProductInfoItemAdapter
import ru.akumakeito.presentation.viewmodel.ProductViewModel
import ru.akumakeito.util.StringUtil.Companion.getAvailableString
import ru.akumakeito.util.StringUtil.Companion.getFeedbackString
import ru.akumakeito.util.StringUtil.Companion.getHideOrShowText

@AndroidEntryPoint
class FragmentAboutItem() : Fragment() {

    private val productViewModel: ProductViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAboutItemBinding.inflate(inflater, container, false)

        val productId = arguments.let {
            it?.getString("PRODUCT_ID_KEY")
        }
        productViewModel.getProductById(productId ?: "")


        productViewModel.product.observe(viewLifecycleOwner) { product ->
            binding.apply {

                itemImages.adapter = ItemImageAdapter(product.imageList, object :
                    OnInteractionListener {}, product)
                TabLayoutMediator(tabLayout,itemImages) { tab, position -> }.attach()

                tvOldPrice.text = requireContext().getString(
                    R.string.price_with_unit, product.price.price, product.price.unit
                )
                tvNewPrice.text = requireContext().getString(
                    R.string.price_with_unit, product.price.priceWithDiscount, product.price.unit
                )
                tvDiscountPercent.text = requireContext().getString(
                    R.string.discount_percent, product.price.discount
                )
                tvTitle.text = product.title
                tvSubtitle.text = product.subtitle

                tvAvailableItems.text = requireContext().getString(
                    R.string.available_items,
                    product.available,
                    getAvailableString(product.available)
                )

                tvBrand.text = product.title
                tvDescription.text = product.description

                var linesDesc = 0
                var linesIngr = 2

                tvDescription.post {
                    linesDesc = tvDescription.lineCount
                }

                tvIngredients.post {
                    linesIngr = tvIngredients.lineCount
                }

                tvIngredients.apply {
                    tvIngredients.apply {
                        if (tvHideOrShowIngr.text == resources.getString(R.string.hide)) {
                            setLines(2)
                            ellipsize = TextUtils.TruncateAt.END
                        } else {
                            ellipsize = TextUtils.TruncateAt.MARQUEE
                            setLines(linesIngr)
                        }
                        text = product.ingredients
                    }

                    tvNewPriceOnBtn.text = requireContext().getString(
                        R.string.price_with_unit,
                        product.price.priceWithDiscount,
                        product.price.unit
                    )

                    tvOldPriceOnBtn.text = requireContext().getString(
                        R.string.price_with_unit, product.price.price, product.price.unit
                    )

                    val productInfoAdapter = ProductInfoItemAdapter()

                    rvProductInfo.adapter = productInfoAdapter

                    productInfoAdapter.submitList(product.info)


                    tvHideOrShowIngr.setOnClickListener {
                        tvIngredients.apply {
                            if (tvHideOrShowIngr.text == resources.getString(R.string.hide)) {
                                setLines(2)
                                ellipsize = TextUtils.TruncateAt.END
                            } else {
                                ellipsize = TextUtils.TruncateAt.MARQUEE
                                setLines(linesIngr)
                            }


                            tvHideOrShowIngr.setText(
                                getHideOrShowText(
                                    requireContext(),
                                    tvHideOrShowIngr.text
                                )
                            )
                        }
                    }

                    tvHideOrShowDesc.setOnClickListener {
                        tvDescription.apply {
                            tvHideOrShowDesc.setText(
                                getHideOrShowText(
                                    requireContext(),
                                    tvHideOrShowDesc.text
                                )
                            )

                            if (tvHideOrShowDesc.text == resources.getString(R.string.show)) {
                                setLines(2)
                                ellipsize = TextUtils.TruncateAt.END
                            } else {
                                ellipsize = TextUtils.TruncateAt.MARQUEE
                                Log.d("tvDescription", "${linesDesc}")
                                setLines(linesDesc)
                            }
                        }
                    }

                    topAppBar.apply {
                        setNavigationOnClickListener {
                            findNavController().navigateUp()
                        }
                    }

                    ratingBar.rating = product.feedback?.rating ?: 0f
                    tvRating.text = product.feedback?.rating.toString()
                    tvCommentsNum.text = requireContext().getString(
                        R.string.item_number,
                        product.feedback?.count ?: 0,
                        getFeedbackString(product.feedback?.count ?: 0)
                    )

                    btnFavorite.isChecked = product.isFavorite

                    btnFavorite.setOnClickListener {
                        productViewModel.updateFavoriteProduct(product)
                    }
                }
            }
        }

        return binding.root
    }

}