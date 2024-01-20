package com.karimali.baseapp.common.extensions

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.content.res.Resources
import android.net.Uri
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.util.TypedValue
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.androidbuffer.kotlinfilepicker.KotConstants
import com.androidbuffer.kotlinfilepicker.KotRequest
import com.androidbuffer.kotlinfilepicker.KotResult
import com.facebook.shimmer.BuildConfig
import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton
import com.google.android.material.snackbar.Snackbar
import com.karimali.baseapp.R
import com.karimali.baseapp.common.models.ResultState
import com.karimali.baseapp.common.utils.Constants
import com.karimali.baseapp.common.utils.Constants.FilePickerConst.REQUEST_CODE_CAVER_PHOTO
import com.karimali.baseapp.common.utils.Constants.FilePickerConst.REQUEST_CODE_DOC
import com.karimali.baseapp.common.utils.Constants.FilePickerConst.REQUEST_CODE_PHOTO
import com.karimali.baseapp.common.utils.Constants.FilePickerConst.REQUEST_CODE_PROFILE_PHOTO
import com.karimali.baseapp.ui.adapters.GenericRecyclerAdapter
import com.karimali.baseapp.ui.base.BaseFragment
import com.karimali.baseapp.ui.custom.ShimmerdRecyclerView
import java.io.*
import java.util.*
import kotlin.random.Random


@ColorInt
fun Context.getColorFromAttr(
    @AttrRes attrColor: Int,
    typedValue: TypedValue = TypedValue(),
    resolveRefs: Boolean = true
): Int {
    theme.resolveAttribute(attrColor, typedValue, resolveRefs)
    return typedValue.data
}

fun Fragment.registerActivityResult(onResult : (Boolean,Int,Intent?) -> Unit) : ActivityResultLauncher<Intent> {
    return registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        val resultCode = result.resultCode
        val data = result.data
        when (resultCode) {
            Activity.RESULT_OK -> {
                onResult(true,resultCode,data)
            }
            else -> {
                onResult(false,resultCode,null)
            }
        }
    }
}

fun Context.isServiceRunning(serviceClassName: String): Boolean {

    val manager = ContextCompat.getSystemService(
        this,
        ActivityManager::class.java
    ) ?: return false

    return manager.getRunningServices(Integer.MAX_VALUE).any { serviceInfo -> serviceInfo.service.className.contains(serviceClassName) }
}

fun Activity.openPdf(url:String){
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    startActivity(browserIntent)
}

/**
 * Used To Open Files
 */

fun Activity.openPdf(file:File?) {

    file?.let {
        Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(FileProvider.getUriForFile(this@openPdf, BuildConfig.APPLICATION_ID + ".provider", file), file.getMimeType())
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            try {
                startActivity(this)
            } catch (e: Exception) {

            }
        }
    }
}

//fun Activity.makeACall(phone:String){
//    checkPerm(arrayListOf(Manifest.permission.CALL_PHONE)){ granted ->
//        if(granted){
//            val phoneIntent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phone"))
//            startActivity(phoneIntent)
//        }else{
//           // Toast.makeText(applicationContext,getString(R.string.you_denied_perm), Toast.LENGTH_LONG).show()
//        }
//    }
//}
//fun Context.makeACall(phone:String){
//    checkPerm(arrayListOf(Manifest.permission.CALL_PHONE)){ granted ->
//        if(granted){
//            val phoneIntent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phone"))
//            startActivity(phoneIntent)
//        }else{
//           // Toast.makeText(applicationContext,getString(R.string.you_denied_perm), Toast.LENGTH_LONG).show()
//        }
//    }
//}

fun Activity.openWhatsApp(number: String,onFailure: () -> Unit) {
    val intent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse("https://wa.me/$number")
    )

    fun  isWhatsAppBussInstalled() :  Boolean {
        return try {
            packageManager.getPackageInfo("com.whatsapp.w4b", PackageManager.GET_ACTIVITIES)
            intent.setPackage("com.whatsapp.w4b")
            true
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            false
        }
    }

    fun  isWhatsAppInstalled() :  Boolean {
        return try {
            packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
            intent.setPackage("com.whatsapp")
            true
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            false
        }
    }

    try {
        when {
            isWhatsAppInstalled() -> {
                startActivity(intent)
            }
            isWhatsAppBussInstalled() -> {
                startActivity(intent)
            }
            else -> {
                onFailure()
                return
            }
        }
    }catch (e:Exception){
        e.printStackTrace()
        onFailure()
    }
}
fun getFilePathFromURI(context: Context?, contentUri: Uri?): String? {
    //copy file and send new file path
    val IMAGE_DIRECTORY = "/demonuts_upload_gallery";
    val fileName = getFileName(contentUri)
    val wallpaperDirectory: File = File(
        "${Environment.getExternalStorageDirectory()}$IMAGE_DIRECTORY"
    )
    // have the object build the directory structure, if needed.
    if (!wallpaperDirectory.exists()) {
        wallpaperDirectory.mkdirs()
    }
    if (!TextUtils.isEmpty(fileName)) {
        val copyFile = File(wallpaperDirectory.toString() + File.separator + fileName)
        // create folder if not exists
        copy(context!!, contentUri, copyFile)
        return copyFile.absolutePath
    }
    return null
}
fun getFileName(uri: Uri?): String? {
    if (uri == null) return null
    var fileName: String? = null
    val path = uri.path
    val cut = path!!.lastIndexOf('/')
    if (cut != -1) {
        fileName = path.substring(cut + 1)
    }
    return fileName
}

fun copy(context: Context, srcUri: Uri?, dstFile: File?) {
    try {
        val inputStream = context.contentResolver.openInputStream(srcUri!!)
            ?: return
        val outputStream: OutputStream = FileOutputStream(dstFile)
        copystream(inputStream, outputStream)
        inputStream.close()
        outputStream.close()
    } catch (e: IOException) {
        e.printStackTrace()
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
}

@Throws(java.lang.Exception::class, IOException::class)
fun copystream(input: InputStream?, output: OutputStream?): Int {
    val BUFFER_SIZE = 1024 * 2;
    val buffer = ByteArray(BUFFER_SIZE)
    val `in` = BufferedInputStream(input, BUFFER_SIZE)
    val out = BufferedOutputStream(output, BUFFER_SIZE)
    var count = 0
    var n = 0
    try {
        while (`in`.read(buffer, 0, BUFFER_SIZE).also { n = it } != -1) {
            out.write(buffer, 0, n)
            count += n
        }
        out.flush()
    } finally {
        try {
            out.close()
        } catch (e: IOException) {
//            Log.e(e.getMessage(), java.lang.String.valueOf(e))
        }
        try {
            `in`.close()
        } catch (e: IOException) {
//            Log.e(e.getMessage(), java.lang.String.valueOf(e))
        }
    }
    return count
}

fun <T : Any>Fragment.stateHandler(
    result: ResultState<T>,
    withShimmer : ShimmerdRecyclerView<T>? = null,
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

fun Context.makeCustomTintColorStateList(color : Int) : ColorStateList = ColorStateList(Constants.states, intArrayOf(color))

fun Context.makeCustomTintColorStateList(states:IntArray,color : Int) : ColorStateList = ColorStateList(arrayOf(states), intArrayOf(color))

fun View.showErrorToast(msg:String){
    //DynamicToast.makeError(requireContext(),msg,1000).show()
    showSnake(msg,colorRes = R.color.red,icon = R.drawable.ic_error)
}

fun Fragment.showErrorToast(msg:String){
    //DynamicToast.makeError(requireContext(),msg,1000).show()
    try {
        requireView().showSnake(msg,colorRes = R.color.red,icon = R.drawable.ic_error)
    }catch (e:Exception){

    }
}

fun Fragment.showNetworkErrorToast(msg:String){
    //DynamicToast.makeError(requireContext(),msg,1000).show()
    requireView().showSnake(msg,colorRes = R.color.red,icon = R.drawable.ic_wifi_off)
}

fun Fragment.showSuccessToast(msg:String){
    //DynamicToast.makeSuccess(requireContext(),msg,1000).show()
    requireView().showSnake(msg,colorRes = R.color.green_color,icon = R.drawable.ic_check_white)
}

fun Fragment.showNormalToast(msg:String){
    //Toast.makeText(requireContext(),msg, Toast.LENGTH_LONG).show()
    requireView().showSnake(msg,color=null,icon=null)
}

fun Fragment.showColoredToast(msg:String,color: Int){
    //Toast.makeText(requireContext(),msg, Toast.LENGTH_LONG).show()
    requireView().showSnake(msg,color,icon = null)
}

@SuppressLint("UseCompatTextViewDrawableApis")
private fun View.showSnake(msg:String,color:Int? = null,colorRes:Int? = null, icon:Int?){
    val snakeBar: Snackbar = Snackbar.make(this, msg , Snackbar.LENGTH_LONG)
    icon?.let {
        val snakeBarLayout = snakeBar.view
        val textView = snakeBarLayout.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0)
        textView.compoundDrawableTintList = ContextCompat.getColorStateList(context,R.color.white)
        textView.compoundDrawablePadding = resources.getDimensionPixelOffset(R.dimen.snack_bar_icon_padding)
    }

    val tintColor = if(colorRes != null)
        ContextCompat.getColor(this.context,colorRes)
    else
        color

    snakeBar.setBackgroundTint(tintColor!!)

    snakeBar.show()
}

fun Context.copyToClipboard(clipLabel: String, text: CharSequence){
    val clipboard = ContextCompat.getSystemService(this, ClipboardManager::class.java)
    clipboard?.setPrimaryClip(ClipData.newPlainText(clipLabel, text))
}

fun Fragment.onBackButtonPressed(onPress : () -> Unit) {
    requireActivity().onBackPressedDispatcher.addCallback(this,object :
        OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            onPress()
        }
    })
}

fun Context.getLocalizedResources(desiredLocale: Locale?): Resources? {
    var conf: Configuration = resources.configuration
    conf = Configuration(conf)
    conf.setLocale(desiredLocale)
    val localizedContext = createConfigurationContext(conf)
    return localizedContext.resources
}

fun Fragment.setupActionsMenu(menuRes:Int,color: Int? = null,onItemSelected : (MenuItem) -> Boolean){
    val menuHost: MenuHost = requireActivity()
    menuHost.addMenuProvider(object : MenuProvider {

        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(menuRes, menu)
            color?.let { guardedColor ->
                 menu.forEach {
                     val spannable = SpannableString(it.title)
                     spannable.setSpan(
                         ForegroundColorSpan(guardedColor),
                         0,
                         spannable.length,
                         Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                     it.title = spannable
                 }
            }
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            if(menuItem.itemId == android.R.id.home){
                (this@setupActionsMenu as BaseFragment<*>).navController?.navigateUp()
                return true
            }
            return onItemSelected(menuItem)
        }
    }, viewLifecycleOwner, Lifecycle.State.RESUMED)
}

fun Int.getNumberSuffix(appContext:Context) : String {
    if (this <=0 ) return ""
    return when(this){
        1 -> "first"
        2 -> "2nd"
        3 -> "3rd"
        else -> "${this}th"
    }
}

fun Context.sendTextData(data:String){
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, data)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    startActivity(shareIntent)
}


fun RecyclerView.enableSwipeToDelete(directions : Int ,onSwiped : (Int) -> Unit): ItemTouchHelper {
    val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
        ItemTouchHelper.SimpleCallback(
            0,
            directions
        ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
            val position = viewHolder.adapterPosition
            onSwiped(position)
        }
    }

    val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
    itemTouchHelper.attachToRecyclerView(this)
    return itemTouchHelper
}
