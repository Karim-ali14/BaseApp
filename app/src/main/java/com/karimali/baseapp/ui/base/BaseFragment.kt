package com.karimali.baseapp.ui.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.ColorStateList
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.viewbinding.ViewBinding
import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton
import com.karimali.baseapp.ui.dialogs.LoadingDialog
import com.google.android.material.snackbar.Snackbar
import android.widget.TextView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.karimali.baseapp.R
import com.karimali.baseapp.common.extensions.gone
import com.karimali.baseapp.common.extensions.restore
import com.karimali.baseapp.common.extensions.visible
import com.karimali.baseapp.common.models.ResultState
import com.karimali.baseapp.ui.custom.ShimmerdRecyclerView
import com.karimali.baseapp.ui.activities.MainActivity
import com.karimali.baseapp.ui.adapters.GenericRecyclerAdapter
import com.skydoves.androidveil.VeilRecyclerFrameView


typealias Inflate<T> = (LayoutInflater,ViewGroup? , Boolean) -> T
open class BaseFragment<T : ViewBinding>(private val inflate : Inflate<T>, layoutId:Int) : Fragment(layoutId) {

    private lateinit var connectivityManager : ConnectivityManager
    protected var binding : T ?= null
    lateinit var emptyDialog: LoadingDialog
    var navController : NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflate(inflater,container,false)
        return binding!!.root
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        navController = try {
            view.findNavController()
        }catch (e:Exception){
            null
        }
        emptyDialog = LoadingDialog()

        super.onViewCreated(view, savedInstanceState)

    }

    protected fun <A:Activity>getFragmentHost(): A {
        return (requireActivity() as A)
    }

    fun <T>Fragment.stateHandler(
        result: ResultState<T>,
        loadingButton: CircularProgressButton? = null,
        isWithDialog: Boolean? = false,
        showToasts:Boolean? = true,
        onLoading: () -> Unit = {},
        onSuccess: (T?) -> Unit = {},
        onPending: (T?) -> Unit = {},
        onUnComplete: (T?) -> Unit = {},
        onBlock: (T?) -> Unit = {},
        onFailure: (String?) -> Unit = {},
    ) {
        when(result){
            is ResultState.EmptyData -> {
                loadingButton?.restore()
                if(showToasts!!){
                    showErrorToast(result.errorMsg.toString())
                    //DynamicToast.makeError(requireContext(),result.errorMsg ,1000).show()
                }
                onFailure(result.errorMsg)

                if(isWithDialog!!)
                    emptyDialog.dismiss()

            }
            is ResultState.Error -> {
                loadingButton?.restore()
                if(showToasts!!){
                    showErrorToast(result.error)
                    //DynamicToast.makeError(requireContext(),result.error.toString() ,1000).show()
                }
                onFailure(result.error)
                if(isWithDialog!!)
                    emptyDialog.dismiss()

            }
            ResultState.Loading -> {
                loadingButton?.startAnimation()
                if(isWithDialog!!)
                {
                    if(!emptyDialog.isAdded){
                        emptyDialog.show(childFragmentManager, LoadingDialog.TAG)
                    }
                }
                onLoading()
            }
            is ResultState.NetworkException -> {
                loadingButton?.restore()
                //showNetworkErrorToast(getString(R.string.no_connection))
                //DynamicToast.makeError(requireContext(),"No Connection",1000).show()
                onFailure(result.errorMsg)
                if(isWithDialog!!)
                    emptyDialog.dismiss()
            }

            is ResultState.Success -> {
                loadingButton?.restore()

                onSuccess(result.data)

                if(isWithDialog!!)
                    emptyDialog.dismiss()

                if(showToasts!!){
                    result.successMsg?.let {
                        showSuccessToast(result.successMsg.toString())
                    }
                }

            }
            is ResultState.UnComplete -> {
                loadingButton?.restore()

                onUnComplete(result.data)

                if(isWithDialog!!)
                    emptyDialog.dismiss()

                if(showToasts!!){
                    result.msg?.let {
                        showSuccessToast(result.msg.toString())
                    }
                }
            }
            is ResultState.Pending -> {
                loadingButton?.restore()

                onPending(result.data)

                if(isWithDialog!!)
                    emptyDialog.dismiss()

                if(showToasts!!){
                    result.msg?.let {
                        showSuccessToast(result.msg.toString())
                    }
                }
            }
            is ResultState.Blocked -> {
                loadingButton?.restore()

                onBlock(result.data)

                if(isWithDialog!!)
                    emptyDialog.dismiss()

                if(showToasts!!){
                    result.msg?.let {
                        showSuccessToast(result.msg.toString())
                    }
                }
            }
            is ResultState.AuthError -> {
                loadingButton?.restore()
                if(showToasts!!){
                    showErrorToast(result.error)
                    //DynamicToast.makeError(requireContext(),result.error.toString() ,1000).show()
                }
                onFailure(result.error)
                if(isWithDialog!!)
                    emptyDialog.dismiss()

            }
            else -> {}
        }
    }

    fun <T : Any>Fragment.stateHandler(
        result: ResultState<T>,
        withShimmer : ShimmerdRecyclerView<T> ? = null,
        withSwipe: SwipeRefreshLayout? = null,
        adapter: GenericRecyclerAdapter<Any>?= null,
        withPlaceHolder : View? = null,
        onLoading: () -> Unit = {},
        onComplete: () -> Unit = {},
        onSuccess: (T?) -> Unit = {},
        onSuccessWithMessage: (T?,String,Boolean) -> Unit = {d,m,s->},
        onFailure: (String?) -> Unit = {},
        onEmptyData: () -> Unit = {}
    ) {

        when(result){
            is ResultState.EmptyData -> {
                onEmptyData()
                onFailure(result.errorMsg)
                withPlaceHolder?.visible()
                withShimmer?.gone()
                withShimmer?.shimmered(false)
                withSwipe?.isRefreshing = false
            }
            is ResultState.Error -> {
                onFailure(result.error)
                withPlaceHolder?.visible()
                withShimmer?.shimmered(false)
                withShimmer?.gone()
                withSwipe?.isRefreshing = false
            }
            ResultState.Loading -> {
                withPlaceHolder?.gone()
                withShimmer?.shimmered(true)
                onLoading()
                withSwipe?.isRefreshing = true
            }
            ResultState.Complete -> {
                onComplete()
                withSwipe?.isRefreshing = false
            }
            is ResultState.NetworkException -> {
                onFailure(result.errorMsg)
                withShimmer?.gone()
                withShimmer?.shimmered(false)
                withSwipe?.isRefreshing = false
            }
            is ResultState.Success -> {
                onSuccess(result.data)
                onSuccessWithMessage(result.data,result.successMsg?:"",result.status?:false)
                withPlaceHolder?.gone()
                withShimmer?.visible()
                withShimmer?.shimmered(false)
                withSwipe?.isRefreshing = false

                if(adapter is GenericRecyclerAdapter<*>){
                    val data = result.data as ArrayList<T>
                    adapter.updateDateGeneric(data)
                }
            }
            is ResultState.AuthError -> {
                onFailure(result.error)
                withPlaceHolder?.visible()
                withShimmer?.gone()
                withShimmer?.shimmered(false)
            }

            else -> {}
        }
    }

    fun <T : Any , A>Fragment.stateHandlerAdapter(
        result: ResultState<T>,
        shimmerRv : VeilRecyclerFrameView ? = null,
        adapter: A ?= null,
        withPlaceHolder : View? = null,
        onLoading: () -> Unit = {},
        onSuccess: (T?) -> Unit = {},
        onFailure: (String?) -> Unit = {},
    ) {

        when(result){
            is ResultState.EmptyData -> {
                onFailure(result.errorMsg)
                withPlaceHolder?.visible()
                shimmerRv?.unVeil()
            }
            is ResultState.Error -> {
                onFailure(result.error)
                withPlaceHolder?.visible()
                shimmerRv?.unVeil()
            }
            ResultState.Loading -> {
                withPlaceHolder?.gone()
                shimmerRv?.veil()
                onLoading()
            }
            is ResultState.NetworkException -> {
                onFailure(result.errorMsg)
                shimmerRv?.unVeil()
            }
            is ResultState.Success -> {
                onSuccess(result.data)
                withPlaceHolder?.gone()
                shimmerRv?.unVeil()

            }
            is ResultState.AuthError -> {
                onFailure(result.error)
                withPlaceHolder?.visible()
                shimmerRv?.unVeil()
            }

            else -> {}
        }
    }

    private fun dismissEmptyDialog(tag:String){
        val prev: Fragment = childFragmentManager.findFragmentByTag(tag)!!
        val df = prev as DialogFragment
        df.dismiss()
    }

    protected fun showErrorToast(msg:String){
        //DynamicToast.makeError(requireContext(),msg,1000).show()
        showSnake(msg,colorRes = R.color.red,icon = R.drawable.ic_baseline_error_24)
    }

    protected fun showSuccessToast(msg:String){
        //DynamicToast.makeSuccess(requireContext(),msg,1000).show()
        showSnake(msg,colorRes = R.color.green_color,icon = R.drawable.ic_baseline_check_white_24)
    }

    protected fun showNormalToast(msg:String){
        //Toast.makeText(requireContext(),msg, Toast.LENGTH_LONG).show()
        showSnake(msg,color=null,icon=null)
    }

    protected fun showColoredToast(msg:String,color: Int){
        //Toast.makeText(requireContext(),msg, Toast.LENGTH_LONG).show()
        showSnake(msg,color,icon = null)
    }

    @SuppressLint("UseCompatTextViewDrawableApis")
    private fun showSnake(msg:String,color:Int? = null,colorRes:Int? = null, icon:Int?){
        val snakeBar: Snackbar = Snackbar.make(requireView(), msg , Snackbar.LENGTH_LONG)
        icon?.let {
            val snakeBarLayout = snakeBar.view
            val textView = snakeBarLayout.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
            textView.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                textView.compoundDrawableTintList = getColorList(R.color.white)
            }
            textView.compoundDrawablePadding = resources.getDimensionPixelOffset(R.dimen.snackbar_icon_padding)
        }

        val tintColor = if(colorRes != null)
            ContextCompat.getColor(requireContext(),colorRes)
        else
            color

        snakeBar.setBackgroundTint(tintColor!!)

        snakeBar.show()
    }

    protected fun getColorList(colorId:Int) : ColorStateList {
        return ColorStateList.valueOf(ContextCompat.getColor(requireContext(), colorId))
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        useButtonHomeBack{
//            requireActivity().onBackPressed()
//        }
//    }

    protected fun handleOnOptionMenuSelection(){

    }

    fun useButtonHomeBack(action:() -> Unit){
        (requireActivity() as MainActivity).binding.toolbar2.setNavigationOnClickListener {
            action()
        }
    }

}