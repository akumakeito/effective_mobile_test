package ru.akumakeito.presentation.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.akumakeito.effectivemobile_test.R
import ru.akumakeito.effectivemobile_test.databinding.CardItemBinding
import ru.akumakeito.effectivemobile_test.domain.model.Product

interface OnCardInteractionListener {
    fun onCardClick(product: Product) {}
    fun onFavoriteClick(product: Product) {}

}

class CardItemAdapter(
    @ApplicationContext private val context: Context,
    private val onCardInteractionListener: OnCardInteractionListener
) : ListAdapter<Product, CardItemAdapter.ProductViewHolder>(ProductDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = CardItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding, onCardInteractionListener, context)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class ProductViewHolder(
        private val binding: CardItemBinding,
        private val listener: OnCardInteractionListener,
        @ApplicationContext private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.apply {

                itemImages.adapter = ItemImageAdapter(product.imageList, object : OnInteractionListener {
                    override fun onCardImageClick(product: Product) {
                        listener.onCardClick(product)
                    }}, product )

                TabLayoutMediator(tabLayout,itemImages) { tab, position -> }.attach()

                tvOldPrice.text = context.getString(
                    R.string.price_with_unit, product.price.price, product.price.unit
                )
                tvNewPrice.text = context.getString(
                    R.string.price_with_unit, product.price.priceWithDiscount, product.price.unit
                )
                tvDiscountPercent.text = context.getString(
                    R.string.discount_percent, product.price.discount
                )
                tvTitle.text = product.title
                tvSubtitle.text = product.subtitle

                if (product.feedback != null) {
                    tvRating.text = product.feedback.rating.toString()
                    tvCommentsNum.text = context.getString(
                        R.string.feedback_count, product.feedback.count
                    )
                } else {
                    ratingLayout.visibility = View.GONE
                }

                btnFavorite.isChecked = product.isFavorite

                itemCard.setOnClickListener {
                    listener.onCardClick(product)
                }
                itemImages.setOnClickListener {
                    listener.onCardClick(product)
                }

                btnFavorite.setOnClickListener {
                    listener.onFavoriteClick(product)
                }

            }
        }
    }
}

class ProductDiffCallBack : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }

}

