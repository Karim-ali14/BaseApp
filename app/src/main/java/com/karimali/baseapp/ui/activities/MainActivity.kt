package com.karimali.baseapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.karimali.baseapp.R
import com.karimali.baseapp.common.extensions.PickSelectionCallback
import com.karimali.baseapp.common.extensions.handleToolBarProcess
import com.karimali.baseapp.common.extensions.setUpBottomNavWithNavController
import com.karimali.baseapp.common.extensions.setUpWithNavigation
import com.karimali.baseapp.common.utils.Constants
import com.karimali.baseapp.common.utils.Constants.FilePickerConst.REQUEST_CODE_CAVER_PHOTO
import com.karimali.baseapp.common.utils.Constants.FilePickerConst.REQUEST_CODE_DOC
import com.karimali.baseapp.common.utils.Constants.FilePickerConst.REQUEST_CODE_PHOTO
import com.karimali.baseapp.common.utils.Constants.FilePickerConst.REQUEST_CODE_PROFILE_PHOTO
import com.karimali.baseapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    var onPickImageResult : PickSelectionCallback? = null
    var onPickProfileImagesResult : PickSelectionCallback? = null
    var onPickFileResult : PickSelectionCallback? = null

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            Log.i("requestCode",requestCode.toString())
            when(requestCode){
                REQUEST_CODE_PHOTO -> { onPickImageResult?.invoke(requestCode,resultCode,data) }
                REQUEST_CODE_PROFILE_PHOTO, REQUEST_CODE_CAVER_PHOTO -> { onPickProfileImagesResult?.invoke(requestCode,resultCode,data) }
                REQUEST_CODE_DOC -> { onPickFileResult?.invoke(requestCode,resultCode,data) }
                else -> {}
            }
        }catch (e:Exception){
            Log.i("File","Not Error")
        }
    }
}