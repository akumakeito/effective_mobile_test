package ru.akumakeito.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.akumakeito.effectivemobile_test.R
import ru.akumakeito.effectivemobile_test.databinding.ActivityMainBinding
import ru.akumakeito.presentation.viewmodel.UserViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private val userViewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        supportActionBar?.hide()

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host) as NavHostFragment

        navController = navHostFragment.navController


        val bottomNavView = binding.bottomNavigation
        setupWithNavController(bottomNavView, navController)


        userViewModel.isSigned.observe(this) {
            Log.d("registrationprob", "is signed ${it}")
            if (it == false) {
                bottomNavView.visibility = View.GONE
                navController.navigate(R.id.fragmentEnterAccount)
            } else {
                bottomNavView.visibility = View.VISIBLE
                navController.navigate(R.id.fragmentHome)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}