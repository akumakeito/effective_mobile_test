package ru.akumakeito.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint
import ru.akumakeito.effectivemobile_test.R
import ru.akumakeito.effectivemobile_test.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        supportActionBar?.hide()

        setContentView(R.layout.activity_main)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.bottomNavigation
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host) as NavHostFragment

        val navController = navHostFragment.navController

        val bottomNavView = binding.bottomNavigation

        bottomNavView.setupWithNavController(navController)



        NavigationBarView.OnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.fragmentHome -> {
                    navController.navigate(R.id.fragmentHome)
                    true
                }
                R.id.fragmentCatalog -> {
                    navController.navigate(R.id.fragmentCatalog)
                    true
                }

                R.id.fragmentCart -> {
                    navController.navigate(R.id.fragmentCart)
                    true
                }

                R.id.fragmentDiscounts -> {
                    navController.navigate(R.id.fragmentDiscounts)
                    true
                }

                R.id.fragmentProfile -> {
                    navController.navigate(R.id.fragmentProfile)
                    true
                }
                else -> false
            }
        }

        navView.setupWithNavController(navController)


    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}