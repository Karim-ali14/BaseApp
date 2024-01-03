package com.karimali.baseapp.ui.activities

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.karimali.baseapp.R
import com.karimali.baseapp.common.extensions.handleToolBarProcess
import com.karimali.baseapp.common.extensions.setUpBottomNavWithNavController
import com.karimali.baseapp.common.extensions.setUpWithNavigation
import com.karimali.baseapp.common.utils.Constants
import com.karimali.baseapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.bottomNav

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        appBarConfiguration = AppBarConfiguration(
            Constants.mainAppScreensWithBottomBar.toSet()
        )

        binding.toolbar.setUpWithNavigation(
            this,
            destinationWithNoBackButton = Constants.destinationWithNoBackButton,
            destinationWithNoToolBar = Constants.destinationWithNoToolBar,
            configuration = appBarConfiguration,
            navController = navController
        )
        setUpBottomNavWithNavController(Constants.mainAppScreensWithBottomBar,navController)

        handleToolBarProcess(
            showSkip = Constants.destinationShowSkipText,
            navController = navController
        )
    }

}