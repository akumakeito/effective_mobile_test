package ru.akumakeito.presentation.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.akumakeito.effectivemobile_test.databinding.ImageSliderBinding
import ru.akumakeito.effectivemobile_test.domain.model.Product

interface OnInteractionListener {
    fun onCardImageClick(product: Product) {}

}

class ItemImageAdapter(
    private val imageResList: List<Int>,
    private val onInteractionListener: OnInteractionListener,
    private val product: Product
) :
    RecyclerView.Adapter<ImageSliderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSliderViewHolder {
        val binding = ImageSliderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ImageSliderViewHolder(binding, onInteractionListener, product)
    }

    override fun getItemCount(): Int = imageResList.size
    override fun onBindViewHolder(holder: ImageSliderViewHolder, position: Int) {
        holder.bind(imageResList[position])
    }
}

class ImageSliderViewHolder(
    private val binding: ImageSliderBinding,
    private val onInteractionListener: OnInteractionListener,
    private val product: Product
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(image: Int) {
        Log.d("pager", image.toString())
        binding.itemImage.setImageResource(image)

        binding.itemImage.setOnClickListener {
            onInteractionListener.onCardImageClick(product)
        }
    }
}
