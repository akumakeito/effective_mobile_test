package ru.akumakeito.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.akumakeito.effectivemobile_test.databinding.ProductInfoItemBinding
import ru.akumakeito.effectivemobile_test.domain.model.ProductInfo

class ProductInfoItemAdapter() : ListAdapter<ProductInfo, ProductInfoViewHolder>(ProductInfoDiffCallBack()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductInfoViewHolder {
        val binding = ProductInfoItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductInfoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}
class ProductInfoViewHolder(
    private val binding: ProductInfoItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(info: ProductInfo) {
        binding.apply {
            infoTitle.text = info.title
            infoValue.text = info.value
        }
    }

}

class ProductInfoDiffCallBack : DiffUtil.ItemCallback<ProductInfo>() {
    override fun areItemsTheSame(oldItem: ProductInfo, newItem: ProductInfo): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: ProductInfo, newItem: ProductInfo): Boolean {
        return oldItem == newItem
    }

}



