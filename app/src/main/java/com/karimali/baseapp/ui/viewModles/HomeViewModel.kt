package com.karimali.baseapp.ui.viewModles

import androidx.lifecycle.MutableLiveData
import com.karimali.baseapp.common.models.ResultState
import com.karimali.baseapp.date.models.home.CategoryModel
import com.karimali.baseapp.date.models.home.HomeDateModel
import com.karimali.baseapp.date.models.home.ProductModel
import com.karimali.baseapp.date.repositories.homeRepo.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
):BaseViewModel() {

    fun fetchHomeDate():MutableLiveData<ResultState<HomeDateModel>>{
        val homeDateFlow = MutableLiveData<ResultState<HomeDateModel>>()
        performNetworkOp(
            startCall = {
                homeDateFlow.postValue(ResultState.Loading)
            }, networkCall = {
                homeRepository.fetchHomeDate()
            }, doOnMainThread = {
                if (it.status ){
                    if (it.data.categories.isEmpty() && it.data.products.isEmpty()
                        && it.data.services.isEmpty()) {
                        homeDateFlow.postValue(ResultState.EmptyData(it.message))
                    }
                    else
                        homeDateFlow.postValue(ResultState.Success(it.data))
                }
            }, onError = {
                homeDateFlow.postValue(handleCommonErrors(it))
            }
        )
        return homeDateFlow
    }
    fun fetchCategoriesDate():MutableLiveData<ResultState<ArrayList<CategoryModel>>>{
        val homeDateFlow = MutableLiveData<ResultState<ArrayList<CategoryModel>>>()
        performNetworkOp(
            startCall = {
                homeDateFlow.postValue(ResultState.Loading)
            }, networkCall = {
                homeRepository.fetchAllCategoryData()
            }, doOnMainThread = {
                if (it.status) {
                    if (it.data.isEmpty()) {
                        homeDateFlow.postValue(ResultState.EmptyData(it.message))
                    }
                    else
                        homeDateFlow.postValue(ResultState.Success(it.data))
                }
            }, onError = {
                homeDateFlow.postValue(handleCommonErrors(it))
            }
        )
        return homeDateFlow
    }
    fun fetchAllProductData(tagId:String):MutableLiveData<ResultState<ArrayList<ProductModel>>>{
        val homeDateFlow = MutableLiveData<ResultState<ArrayList<ProductModel>>>()
        performNetworkOp(
            startCall = {
                homeDateFlow.postValue(ResultState.Loading)
            }, networkCall = {
                homeRepository.fetchAllProductData(tagId)
            }, doOnMainThread = {
                if (it.status) {
                    if (it.data.isEmpty()) {
                        homeDateFlow.postValue(ResultState.EmptyData(it.message))
                    }
                    else
                        homeDateFlow.postValue(ResultState.Success(it.data))
                }
            }, onError = {
                homeDateFlow.postValue(handleCommonErrors(it))
            }
        )
        return homeDateFlow
    }
}