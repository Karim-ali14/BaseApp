package com.karimali.baseapp.ui.viewModles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.karimali.baseapp.common.models.ResultState
import com.karimali.baseapp.date.models.ClientModel
import com.karimali.baseapp.date.repositories.authRepo.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
):BaseViewModel() {

    fun login(
        phone: String,
        password: String
    ) : LiveData<ResultState<ClientModel>> {
        val loginDateFlow: MutableLiveData<ResultState<ClientModel>> = MutableLiveData()

        performNetworkOp(
            startCall = { loginDateFlow.postValue(ResultState.Loading) },
            networkCall = { authRepository.login(phone, password) },
            doOnMainThread = { response ->
                val result =
                    if (response.status)
                        ResultState.Success(response.data,response.message)
                    else
                        ResultState.Error(response.message)

                loginDateFlow.value = result
            },
            onError = {
                loginDateFlow.postValue(handleCommonErrors(it))
            }
        )
        return loginDateFlow
    }

    fun registration(
        phone:String,
        firstName:String,
        lastName:String,
        password:String,
        confirmPassword:String,
        code:String,
    ) : LiveData<ResultState<ClientModel>> {
        val loginDateFlow: MutableLiveData<ResultState<ClientModel>> = MutableLiveData()

        performNetworkOp(
            startCall = { loginDateFlow.postValue(ResultState.Loading) },
            networkCall = { authRepository.registration(
                phone = phone ,
                firstName = firstName,
                lastName = lastName,
                password = password,
                confirmPassword = confirmPassword,
                code = code) },
            doOnMainThread = { response ->
                val result =
                    if (response.status)
                        ResultState.Success(response.data,response.message)
                    else
                        ResultState.Error(response.message)

                loginDateFlow.value = result
            },
            onError = {
                loginDateFlow.postValue(handleCommonErrors(it))
            }
        )
        return loginDateFlow
    }

    fun sendCode(
        phone:String
    ): LiveData<ResultState<Any>> {
        val smsDateFlow: MutableLiveData<ResultState<Any>> = MutableLiveData()

        performNetworkOp(
            startCall = { smsDateFlow.postValue(ResultState.Loading) },
            networkCall = { authRepository.sendCode(phone) },
            doOnMainThread = { response ->
                val result =
                    if (response.status )
                        ResultState.Success(response.data,response.message)
                    else
                        ResultState.Error(response.message)

                smsDateFlow.value = result
            },
            onError = {
                smsDateFlow.postValue(handleCommonErrors(it))
            }
        )
        return smsDateFlow
    }

    fun confirmCode(
        phone:String,
        code:String,
    ):LiveData<ResultState<Any>>{
        val verifyCodeFlow: MutableLiveData<ResultState<Any>> = MutableLiveData()

        performNetworkOp(
            startCall = { verifyCodeFlow.postValue(ResultState.Loading) },
            networkCall = { authRepository.confirmCode(phone, code) },
            doOnMainThread = { response ->
                val result =
                    if (response.status)
                        ResultState.Success(response.data,response.message)
                    else
                        ResultState.EmptyData(response.message)

                verifyCodeFlow.value = result
            },
            onError = {
                verifyCodeFlow.postValue(handleCommonErrors(it))
            }
        )
        return verifyCodeFlow
    }


    fun sendCodeForForgetPassword(
        phone:String
    ): LiveData<ResultState<Any>> {
        val smsDateFlow: MutableLiveData<ResultState<Any>> = MutableLiveData()

        performNetworkOp(
            startCall = { smsDateFlow.postValue(ResultState.Loading) },
            networkCall = { authRepository.sendCodeForForgetPassword(phone) },
            doOnMainThread = { response ->
                val result =
                    if (response.status )
                        ResultState.Success(response.data,response.message)
                    else
                        ResultState.Error(response.message)

                smsDateFlow.value = result
            },
            onError = {
                smsDateFlow.postValue(handleCommonErrors(it))
            }
        )
        return smsDateFlow
    }


    fun resetPassword(
        phone:String ,
        code:String ,
        password:String ,
        confirmPassword:String
    ): LiveData<ResultState<Any>> {
        val resetDateFlow: MutableLiveData<ResultState<Any>> = MutableLiveData()

        performNetworkOp(
            startCall = { resetDateFlow.postValue(ResultState.Loading) },
            networkCall = { authRepository.resetPassword(phone, code, password, confirmPassword) },
            doOnMainThread = { response ->
                val result =
                    if (response.status )
                        ResultState.Success(response.data,response.message)
                    else
                        ResultState.Error(response.message)

                resetDateFlow.value = result
            },
            onError = {
                resetDateFlow.postValue(handleCommonErrors(it))
            }
        )
        return resetDateFlow
    }

}