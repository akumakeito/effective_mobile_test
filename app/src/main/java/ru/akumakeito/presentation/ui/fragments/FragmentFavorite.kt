package ru.akumakeito.presentation.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import ru.akumakeito.effectivemobile_test.R
import ru.akumakeito.effectivemobile_test.databinding.FragmentFavoriteBinding
import ru.akumakeito.presentation.ui.adapters.FavoritePagerAdapter
import ru.akumakeito.presentation.viewmodel.ProductViewModel

@AndroidEntryPoint
class FragmentFavorite : Fragment() {

    private val productViewModel: ProductViewModel by viewModels()
    private lateinit var viewPagerAdapter: FavoritePagerAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var binding : FragmentFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        Log.d("tabs", "favorite fragment onCreateView")
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("tabs", "favorite fragment onViewCreated")


        binding.topAppBar.apply {
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }

        viewPager = binding.pager
        tabLayout = binding.tabLayout

        viewPagerAdapter = FavoritePagerAdapter(this)
        viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text =
                if (position == 0) requireContext().getString(R.string.goods) else requireContext().getString(
                    R.string.brands
                )
        }.attach()
    }
}
