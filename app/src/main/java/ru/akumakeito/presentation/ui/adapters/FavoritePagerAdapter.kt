package ru.akumakeito.presentation.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.akumakeito.presentation.ui.fragments.FragmentFavoriteBrands
import ru.akumakeito.presentation.ui.fragments.FragmentFavoriteContainer

class FavoritePagerAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int = 2


    override fun createFragment(position: Int): Fragment = if (position == 0) FragmentFavoriteContainer() else FragmentFavoriteBrands()


}