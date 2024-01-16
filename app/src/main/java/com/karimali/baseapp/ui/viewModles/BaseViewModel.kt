package com.karimali.baseapp.ui.viewModles

import android.net.ConnectivityManager
import android.net.Network
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karimali.baseapp.common.models.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.util.*

@SuppressWarnings("unchecked")
open class BaseViewModel : ViewModel() {

    private lateinit var connectivityManager : ConnectivityManager

    protected val isThereNetwork : MutableLiveData<Boolean> = MutableLiveData(true)

    protected var onNetwork : (Boolean) -> Unit = {}

    val networkCallBack = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            Log.i("ConnectivityManager",network.toString())
            isThereNetwork.postValue(true)
        }
        override fun onLost(network: Network) {
            Log.i("ConnectivityManager",network.toString())
            isThereNetwork.postValue(false)
        }
    }

    protected fun <T>handleCommonErrors(error : Exception?) : ResultState<T> {
         if(error?.message?.contains("host",ignoreCase = true) == true){
             return ResultState.NetworkException(error.message)
            }
        val unExpectedErrMsg : String= if(Locale.getDefault().toString() == "en" ){
            "Something wrong please try again later !!"
        }else{
            "حدث خطا ما برجاء المحاولة مرة اخري"
        }
        Log.e("ApiError","Err : $error")
        return ResultState.Error(unExpectedErrMsg)
    }


    protected fun <T>performNetworkOp(startCall :suspend () -> Unit,networkCall : suspend () -> T,doOnMainThread : suspend (T)-> Unit, onError : suspend (Exception?) -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                startCall()
                val data = networkCall()
                withContext(Dispatchers.Main){
                    doOnMainThread(data)
                }
            }catch (e: Exception){
                e.printStackTrace()
                onError(e)
            }
        }
    }

    protected fun <T,B>performLocaleFirst(networkCall : suspend () -> T ,dataBaseOperation : suspend () -> B ,doOnMainThread : suspend (T)-> Unit,doLocalOnMainThread : suspend (B?)-> Unit,onError : (Exception?) -> Unit){
        viewModelScope.launch {
            try {
                toggleLocaleServerData(connectivityManager.activeNetwork != null,networkCall,dataBaseOperation,doOnMainThread,doLocalOnMainThread)
                onNetwork = {
                    launch {
                        toggleLocaleServerData(it,networkCall,dataBaseOperation,doOnMainThread,doLocalOnMainThread)
                    }
                }
            }catch (e: Exception){
                onError(e)
            }
        }
    }


    private suspend fun <B,T>toggleLocaleServerData(hasNetwork:Boolean, networkCall : suspend () -> T, dataBaseOperation : suspend () -> B, doOnMainThread : suspend (T)-> Unit, doLocalOnMainThread : suspend (B?)-> Unit) {
        if(hasNetwork){
            val network = viewModelScope.async(Dispatchers.IO) {
                networkCall()
            }
            doOnMainThread(network.await())
            Log.i("ConnectivityManager","from network")
        }else{
            Log.i("ConnectivityManager","from local")
            val data = viewModelScope.async(Dispatchers.IO) {
                dataBaseOperation()
            }
            doLocalOnMainThread(data.await())
        }
    }

    override fun onCleared() {
        try {
            if(this::connectivityManager.isInitialized) connectivityManager.unregisterNetworkCallback(networkCallBack)
        }catch (e:Exception){
            Log.i("Dispose","Not Registered By The Way")
        }
        Log.i("Dispose","All Cleared")
        super.onCleared()
    }

    protected fun setupNetworkListener(connectivity:ConnectivityManager) {
        connectivityManager = connectivity
        //connectivityManager.registerDefaultNetworkCallback(networkCallBack)
    }

}