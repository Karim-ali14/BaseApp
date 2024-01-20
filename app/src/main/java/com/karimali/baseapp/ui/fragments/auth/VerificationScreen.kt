package com.karimali.baseapp.ui.fragments.auth

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.forEachIndexed
import androidx.core.view.get
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.karimali.baseapp.R
import com.karimali.baseapp.common.extensions.isCodeVerificationFieldNotEmpty
import com.karimali.baseapp.common.utils.Enums
import com.karimali.baseapp.common.utils.Enums.NavigationTypes.SignUp
import com.karimali.baseapp.databinding.FragmentVerificationScreenBinding
import com.karimali.baseapp.ui.base.BaseFragment
import com.karimali.baseapp.ui.viewModles.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerificationScreen : BaseFragment<FragmentVerificationScreenBinding>
    (FragmentVerificationScreenBinding::inflate,R.layout.fragment_verification_screen) {
    private val args:VerificationScreenArgs by navArgs()
    private val authViewModel: AuthViewModel by viewModels()

    private var phone = ""
    lateinit var tBox1 : EditText
    lateinit var tBox2 : EditText
    lateinit var tBox3 : EditText
    lateinit var tBox4 : EditText
    var userHasAccount = false
    lateinit var timer: CountDownTimer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventClicks()

        bindViews()

        setFocus()

    }

    private fun eventClicks() {
        binding!!.apply {
            sendSummary.setOnClickListener {
                if (validateInputs()){
                    verifyPhone()
                }

            }
        }
    }

    private fun verifyPhone() {
        authViewModel.confirmCode(
            phone,collectCode()
        ).observe(viewLifecycleOwner){
            stateHandler(
                result = it,
                loadingButton = binding!!.sendSummary,
                showToasts = true,
                onSuccess = {
                    if (args.type == Enums.NavigationTypes.SignUp){
                        navigateToCompleteProfile()
                    }else if (args.type == Enums.NavigationTypes.ResetPassword){
                        navigateToResetPassword()
                    }
                }
            )
        }
    }

    private fun navigateToResetPassword() {
        navController!!.navigate(
            VerificationScreenDirections.actionVerificationScreenToNewPasswordScreen(
                phone,collectCode()
            )
        )
    }

    private fun navigateToCompleteProfile() {
        navController!!.navigate(
            VerificationScreenDirections.actionVerificationScreenToCompleteProfileScreen(
                phone,collectCode()
            )
        )
    }

    private fun validateInputs() = tBox1.isCodeVerificationFieldNotEmpty() && tBox2.isCodeVerificationFieldNotEmpty() &&
            tBox3.isCodeVerificationFieldNotEmpty() && tBox4.isCodeVerificationFieldNotEmpty()

    private fun bindViews() {

        tBox1 = binding!!.numBox1
        tBox2 = binding!!.numBox2
        tBox3 = binding!!.numBox3
        tBox4 = binding!!.numBox4

        tBox1.requestFocus()

    }

    private fun setFocus() {
        binding!!.codeBoxesLayout.forEachIndexed { index, view ->
            if(view is EditText){
                val tv = view as EditText
                tv.doOnTextChanged { text, start, before, count ->
                    Log.i("Foc","Text Changed $index")

                    if(index <= 6) {
                        if(text!!.isNotEmpty())
                        {
                            try {
                                binding!!.codeBoxesLayout[index + 2].requestFocus()
                            }catch (e:Exception){

                            }

//                            binding!!.codeBoxesLayout[index].backgroundTintList = getColorList(R.color.app_second_color)
                        }else{
//                            binding!!.codeBoxesLayout[index].backgroundTintList = getColorList(R.color.white)
                        }

                    }
                }
                //For Backspace
                tv.setOnKeyListener { _, keyCode, _ ->
                    if(keyCode == KeyEvent.KEYCODE_DEL && tv.text.isEmpty()) {
                        try {
                            binding!!.codeBoxesLayout[index - 2].requestFocus()
                        }catch (e:Exception){
                            binding!!.codeBoxesLayout[index + 1].requestFocus()
                        }
                    }
                    return@setOnKeyListener false
                }
            }
        }
    }

    private fun collectCode() : String {
        var code = ""
        binding!!.codeBoxesLayout.forEachIndexed { index, view ->
            if(view is EditText){
                val tv = view as EditText
                code += tv.text.toString()
            }
        }
        return  code
    }
}