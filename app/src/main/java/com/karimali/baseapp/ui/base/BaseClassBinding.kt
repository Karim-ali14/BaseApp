package com.karimali.teacherpackage.shared.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseBindingActivity<B : ViewBinding> : AppCompatActivity() {
    lateinit var binding: B


    //val viewModel : MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        // Set theme before setContentView

        //viewModel.setTheme(this)
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
    }

    abstract fun getViewBinding(): B

}