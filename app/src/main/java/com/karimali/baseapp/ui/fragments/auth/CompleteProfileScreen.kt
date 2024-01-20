package com.karimali.baseapp.ui.fragments.auth

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.androidbuffer.kotlinfilepicker.KotConstants
import com.karimali.baseapp.R
import com.karimali.baseapp.common.extensions.confirmPasswordValidation
import com.karimali.baseapp.common.extensions.getValue
import com.karimali.baseapp.common.extensions.handlingFilePickingDataSingle
import com.karimali.baseapp.common.extensions.isEmptyFieldValidation
import com.karimali.baseapp.common.extensions.isEmptyIconValidation
import com.karimali.baseapp.common.extensions.isValidPassword
import com.karimali.baseapp.common.extensions.loadingImageUrl
import com.karimali.baseapp.common.extensions.nameValidation
import com.karimali.baseapp.common.extensions.pickImages
import com.karimali.baseapp.common.extensions.toStringRequestBody
import com.karimali.baseapp.common.utils.Constants
import com.karimali.baseapp.common.utils.Constants.FilePickerConst.REQUEST_CODE_PROFILE_PHOTO
import com.karimali.baseapp.databinding.FragmentCompleteProfileScreenBinding
import com.karimali.baseapp.ui.activities.MainActivity
import com.karimali.baseapp.ui.base.BaseFragment
import com.karimali.baseapp.ui.viewModles.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.RequestBody
import java.io.File

@AndroidEntryPoint
class CompleteProfileScreen : BaseFragment<FragmentCompleteProfileScreenBinding>
    (FragmentCompleteProfileScreenBinding::inflate,R.layout.fragment_complete_profile_screen) {
    private val authViewModel:AuthViewModel by viewModels()
    private var selectedUserImage :String? = null
    private val args:CompleteProfileScreenArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventClicks()
        listingToPickFile()
    }

    private fun validateDate(): Boolean {
        var validate = false
        binding!!.apply {
            if (
                selectedUserImage.isEmptyIconValidation(binding!!.userImg) &&
                firstNameInput.nameValidation() &&
                lastNameInput.nameValidation() &&
                passwordInput.isValidPassword() &&
                confirmPasswordInput.isValidPassword() &&
                passwordInput.confirmPasswordValidation(confirmPasswordInput)
            )
                validate = true

            return validate
        }
    }

    private fun eventClicks() {
        binding!!.apply {

            userImg.setOnClickListener {
                requireActivity().pickImages(
                    KotConstants.FILE_TYPE_IMAGE_ALL,
                    false,
                    requestCode = REQUEST_CODE_PROFILE_PHOTO
                )
            }

            signUpBtu.setOnClickListener {
                if (validateDate())
                    authViewModel.registration(
                        getFields(),getImageFile()
                    ).observe(viewLifecycleOwner){
                        stateHandler(
                            result = it,
                            loadingButton = signUpBtu,
                            showToasts = true,
                            onSuccess = {
                                navigateToHomeScreen()
                            }
                        )
                    }
            }
        }
    }

    private fun navigateToHomeScreen() {

    }

    private fun getFields(): HashMap<String, RequestBody> = hashMapOf(
        "phone" to args.phone.toStringRequestBody(),
        "code" to args.code.toStringRequestBody(),
        "first_name" to binding!!.firstNameInput.getEditText().getValue().toStringRequestBody(),
        "last_name" to binding!!.lastNameInput.getEditText().getValue().toStringRequestBody(),
        "password" to binding!!.passwordInput.getEditText().getValue().toStringRequestBody(),
        "confirm_password" to binding!!.confirmPasswordInput.getEditText().getValue().toStringRequestBody()
        )

    private fun getImageFile(): File = File(selectedUserImage?:"")

    private fun listingToPickFile() {
        (requireActivity() as MainActivity).onPickProfileImagesResult = { requestCode, resultCode, data ->
            requireActivity().handlingFilePickingDataSingle(requestCode, resultCode, data).apply {
                this?.let {
                        Log.i("imageUrl",it)
                        selectedUserImage = it
                        binding!!.userImg.loadingImageUrl(selectedUserImage)
                        selectedUserImage.isEmptyIconValidation(binding!!.userImg)
                }
            }
        }
    }
}