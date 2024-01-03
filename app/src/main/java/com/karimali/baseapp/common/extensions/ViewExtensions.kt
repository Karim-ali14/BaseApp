package com.karimali.baseapp.common.extensions

import android.content.Context
import android.graphics.*
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Interpolator
import android.webkit.URLUtil
import android.widget.*
import androidx.annotation.DrawableRes
import com.google.android.material.appbar.MaterialToolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.core.widget.doOnTextChanged
import androidx.databinding.BindingAdapter
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.*
import androidx.viewpager2.widget.ViewPager2
import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.karimali.baseapp.ui.dialogs.OptionsBottomSheet
import com.skydoves.androidveil.VeilRecyclerFrameView
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.karimali.baseapp.R
import com.karimali.baseapp.common.utils.Constants
import com.karimali.baseapp.common.utils.DividerItemDecorator
import com.karimali.baseapp.di.AppSharedPrefs
import com.karimali.baseapp.ui.activities.MainActivity
import com.karimali.baseapp.ui.adapters.AdapterBindings
import com.karimali.baseapp.ui.adapters.GenericRecyclerAdapter
import com.karimali.baseapp.ui.dialogs.DropDownBottomSheet
import com.tbuonomo.viewpagerdotsindicator.setPaddingHorizontal

typealias OnItemPressed = (Int) -> Unit
fun View?.removeSelf() {
    this ?: return
    val parentView = parent as? ViewGroup ?: return
    parentView.removeView(this)
}

fun TextInputLayout.setPhoneWithCode(phone:String){
    val split = phone.trim().split(" ")
    val phoneCode = split.first()
    this.prefixText = phoneCode
    this.editText?.setText(split.last())
}


fun TextInputLayout.validation(errorMsg:String? = null) : Boolean {
    val textFrame : ViewGroup = this[0] as ViewGroup
    var textInput : TextInputEditText ? = null
    var isValid : Boolean = false

    textFrame.forEach { view ->
        if(view is TextInputEditText){
            textInput = view as TextInputEditText
        }
    }

    Log.i("Views","View $textInput")

    when {
        textInput!!.text!!.trim().isEmpty() -> {
            this.error = errorMsg ?: context.getString(R.string.required)
            isValid = false
        }
        else -> {
            this.error = null
            isValid = true
        }
    }

    textInput!!.doOnTextChanged { text, _ , _ , _ ->
        if(text!!.isNotEmpty()){
            isErrorEnabled = false
            this.error = null
            isValid = true
        }
    }

    return isValid
}

fun TextInputLayout.urlValidation(errorMsg:String = "Not Valid URL") : Boolean {
    val textFrame : ViewGroup = this[0] as ViewGroup
    var textInput : TextInputEditText ? = null
    var isValid : Boolean = false

    textFrame.forEach { view ->
        if(view is TextInputEditText){
            textInput = view as TextInputEditText
        }
    }

    Log.i("Views","View $textInput")

    when {
        textInput!!.text!!.trim().isEmpty() -> {
            this.error = context.getString(R.string.required)
            isValid = false
            return isValid
        }
        !URLUtil.isValidUrl(textInput!!.text!!.trim().toString()) -> {
            this.error = errorMsg
            isValid = false
            return isValid
        }
        else -> {
            this.error = null
            isValid = true
        }
    }

    textInput!!.doOnTextChanged { text, _ , _ , _ ->
        if(text!!.isNotEmpty()){
            isErrorEnabled = false
            this.error = null
            isValid = true
        }
    }

    return isValid
}

fun TextInputLayout.isDateEmpty(textView:TextView?,errorMsg:String = "Required") : Boolean {
    val textFrame : ViewGroup = this[0] as ViewGroup
    var textInput : TextInputEditText ? = null
    var isValid : Boolean = false

    textFrame.forEach { view ->
        if(view is TextInputEditText){
            textInput = view as TextInputEditText
        }
    }

    when {
        textInput!!.text!!.trim().isEmpty() -> {
            this.error = errorMsg
            isValid = false
        }
        textView != null && textView.text.isEmpty() ->{
            Log.i("Views","View ${textView.text.isNullOrEmpty()}")
            this.error = "select date"
            isValid = false
        }
        else -> {
            this.error = null
            isValid = true
        }
    }

    textInput!!.doOnTextChanged { text, _ , _ , _ ->
        if(text!!.isNotEmpty()){
            isErrorEnabled = false
            this.error = null
            isValid = true
        }
    }


    return isValid
}

fun DialogFragment.safeShow(fragmentManager: FragmentManager,tag:String){
    if(!isAdded){
        show(fragmentManager,tag)
    }
}

fun TextInputLayout.getTextInput() : TextInputEditText {
    val textFrame : ViewGroup = this[0] as ViewGroup
    var textInput : TextInputEditText ? = null


    textFrame.forEach { view ->
        if(view is TextInputEditText){
            textInput = view as TextInputEditText
        }
    }



    return textInput!!
}

fun View.toggleEnabled(enabled:Boolean){
    if(enabled){
        this.isClickable = true
        this.isFocusable = true
        this.isEnabled = true
    }else{
        this.isClickable = false
        this.isFocusable = false
        this.isEnabled = false
    }
}

fun TextView.toggleEnabledText(enabled:Boolean){
    if(enabled){
        setTextColor(ContextCompat.getColor(this.context,R.color.dark_blue_color))
        this.isClickable = true
        this.isFocusable = true
        this.isEnabled = true
    }else{
        setTextColor(ContextCompat.getColor(this.context,R.color.hint_color))
        this.isClickable = false
        this.isFocusable = false
        this.isEnabled = false
    }
}

fun View.gone(){
    this.visibility = View.GONE
}

fun View.visible(){
    this.visibility = View.VISIBLE
}

fun View.inVisible(){
    this.visibility = View.INVISIBLE
}

/**
 Conditional View Visibility Toggle **/
fun View.isVisible(isVisible : Boolean){
    this.visibility = if(isVisible) View.VISIBLE else  View.GONE
}


fun View.invisible(){
    this.visibility = View.INVISIBLE
}

fun Spinner.validation(errorMsg: String = "Required") {
    val errorText : TextView = this.selectedView as TextView
    errorText.error = ""
    errorText.setTextColor(Color.RED)
    errorText.text = errorMsg
}

fun CircularProgressButton.restore(){
    this.revertAnimation {
        this.background = ContextCompat.getDrawable(context, R.drawable.btn_bg)
    }
}

fun ViewPager2.moveToNext(){
    this.setCurrentItem(++currentItem,true)
}

fun ViewPager2.moveToLast(){
    this.setCurrentItem(childCount,true)
}

fun ViewPager2.moveTo(pos:Int){
    this.setCurrentItem(pos,true)
}

fun ViewPager2.setupWithControllers(controllers:View){
    Log.i("Recycler","${this.adapter!!.itemCount}")

    this.adapter!!.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            val itemCount = this@setupWithControllers.adapter!!.itemCount
            if(itemCount == 1){
                controllers.gone()
            }else{
                controllers.visible()
            }
        }
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            Log.i("Recycler","${itemCount}")
            if(itemCount == 1){
                controllers.gone()
            }else{
                controllers.visible()
            }
        }
        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            Log.i("Recycler","$itemCount")
            Log.i("Recycler","Removed")
            if(itemCount == 1){
                controllers.gone()
            }else{
                controllers.visible()
            }
        }
    })
}

fun ViewPager2.moveToPrevious(){
    if(currentItem != 0)
        this.setCurrentItem(--currentItem,true)
}

fun LinearLayout.addChildren(views: ArrayList<View>){
    views.forEach {
        this.addView(it)
    }
}

fun ChipGroup.addChildren(views: ArrayList<Chip>){
    views.forEach {
        this.addView(it)
    }
}

fun LinearLayout.addChild(view: View){
    this.addView(view)
}

fun Context.getBitmapFromVector(vectorResId:Int): BitmapDescriptor? {
    return ContextCompat.getDrawable(this, vectorResId)?.run {
        setBounds(0, 0, intrinsicWidth, intrinsicHeight)
        val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
        draw(Canvas(bitmap))
        BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}

fun MainActivity.setUpBottomNavWithNavController(activeDestinations : ArrayList<Int>, navController: NavController) {
    NavigationUI.setupWithNavController(binding.bottomNav,navController)
    binding.bottomNav.setOnNavigationItemReselectedListener {  }
    navController.addOnDestinationChangedListener { controller, destination, arguments ->
        if(activeDestinations.contains(destination.id)) {
            binding.content.setPadding(0,0,0,100)
            binding.bottomAppBar.visible()
        } else {
            binding.content.setPadding(0,0,0,0)
            binding.bottomAppBar.gone()
        }
    }
}

fun MaterialToolbar.setUpWithNavigation(activity:MainActivity,
                                        destinationWithNoBackButton : ArrayList<Int>,
                                        destinationWithNoToolBar:ArrayList<Int>,
                                        configuration : AppBarConfiguration ?= null ,
                                        navController: NavController){
    activity.setSupportActionBar(this)
    activity.setupActionBarWithNavController(navController,configuration!!)
    setNavigationIcon(R.drawable.back_icon)
    navController.addOnDestinationChangedListener { controller, destination, arguments ->
        Log.i("destination",destination.id.toString())

        if(destinationWithNoBackButton.contains(destination.id)){
            setPaddingHorizontal(35)
        }else{
            setPaddingHorizontal(-10)
        }

        if(destinationWithNoToolBar.contains(destination.id)){
            activity.binding.toolbarLayout.gone()
        }
        else {
            activity.binding.toolbarLayout.visible()
        }

        if(!configuration.topLevelDestinations.contains(destination.id)){
//            setNavigationIcon(R.drawable.back_icon)
        }

        isTitleCentered = false
        isSubtitleCentered = false
        setSubtitleTextColor(ContextCompat.getColor(context,R.color.black))
    }
}

fun MainActivity.handleToolBarProcess(
    showSkip: ArrayList<Int> = arrayListOf(),
    navController: NavController,
//    sharedPrefs: AppSharedPrefs
){
    navController.addOnDestinationChangedListener { controller, destination, arguments ->
//        val clientModel = sharedPrefs.getSavedData<ClientModel>(Constants.USER_KEY)
        binding.apply {
            if (showSkip.contains(destination.id)){
                skipTx.visible()
            }else{
                skipTx.gone()
            }
        }
    }
}
fun MaterialToolbar.center(isCenter: Boolean){
    isTitleCentered = isCenter
}

fun MaterialToolbar.title(title: Int){
    setTitle(context.getString(title))
}

fun MaterialToolbar.title(title: String){
    setTitle(title)
}

fun MaterialToolbar.color(color: Int){
    setBackgroundColor(color)
//    setBackgroundResource(if(color == R.color.black) R.color.gray_bg else color)
}

fun <T : Any>RecyclerView.setup(customAdapter: GenericRecyclerAdapter<T>, isLinear:Boolean = true, cols : Int? = 2, isHorizontal : Boolean = false){
    val orientation = if(isHorizontal) RecyclerView.HORIZONTAL else RecyclerView.VERTICAL
    adapter = customAdapter
    layoutManager =   if(isLinear) LinearLayoutManager(context,orientation,false) else GridLayoutManager(context,cols?:2)
}


fun <T : RecyclerView.Adapter<*>>RecyclerView.setup(customAdapter:T,isLinear:Boolean = true,cols : Int? = 2,isHorizontal : Boolean = false){
    val orientation = if(isHorizontal) RecyclerView.HORIZONTAL else RecyclerView.VERTICAL
    adapter = customAdapter
    layoutManager =   if(isLinear) LinearLayoutManager(context,orientation,false) else GridLayoutManager(context,cols?:2)
}

fun <T : Any> VeilRecyclerFrameView.setup(customAdapter:GenericRecyclerAdapter<T>, isLinear:Boolean = true,cols : Int? = 2){
    setAdapter(customAdapter)
    setLayoutManager(if(isLinear) LinearLayoutManager(context) else GridLayoutManager(context,cols?:2))
    addVeiledItems(15)
}

@BindingAdapter(value = ["setup","isLinear","cols"], requireAll = false)
fun <T :RecyclerView.Adapter<*>> VeilRecyclerFrameView.setup(customAdapter:T, isLinear:Boolean = true,cols : Int? = 2){
    setAdapter(customAdapter)
    setLayoutManager(if(isLinear) LinearLayoutManager(context) else GridLayoutManager(context,cols?:2))
    addVeiledItems(15)
}


@BindingAdapter(value = ["setupFlexed","flexDirection","flexJustifyContent"], requireAll = false)
fun <T : Any?>RecyclerView.setupFlexed(customAdapter:GenericRecyclerAdapter<T>,flexDirection: Int,flexJustifyContent: Int){
    val mLayoutManger = FlexboxLayoutManager(context)
    adapter = customAdapter
    mLayoutManger.flexDirection = flexDirection
    mLayoutManger.justifyContent = flexJustifyContent
    layoutManager =  mLayoutManger
}

fun <T : Any?>RecyclerView.setupStaggered(customAdapter:GenericRecyclerAdapter<T>,cols: Int?){
    val mLayoutManger = StaggeredGridLayoutManager(cols ?: 2,StaggeredGridLayoutManager.VERTICAL)
    mLayoutManger.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
    adapter = customAdapter
    layoutManager =  mLayoutManger
}

fun <T : Any?>RecyclerView.setupStaggered(customAdapter:GenericRecyclerAdapter<T>,cols: Int?,shimmerEnable : Boolean ?= false , shimmerInitialItems : Int ?= 5 , shimmerLayoutResource: Int){

    val mLayoutManger = StaggeredGridLayoutManager(cols ?: 2,StaggeredGridLayoutManager.VERTICAL)
    mLayoutManger.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
    val dummyItems = List(shimmerInitialItems ?: 5) { index -> null }

    val shimmerAdapter = GenericRecyclerAdapter(
        dummyItems.toArrayList(),
        shimmerLayoutResource,
        AdapterBindings.shimmerBinding()
    )

    adapter = if(shimmerEnable == true) shimmerAdapter else customAdapter
    layoutManager =  mLayoutManger
}

@BindingAdapter(value = ["setupFlexed","flexDirection","flexJustifyContent"], requireAll = false)
fun <T :RecyclerView.Adapter<*>>RecyclerView.setupFlexed(customAdapter:T,flexDirection: Int,flexJustifyContent: Int){
    val mLayoutManger = FlexboxLayoutManager(context)
    adapter = customAdapter
    mLayoutManger.flexDirection = flexDirection
    mLayoutManger.justifyContent = flexJustifyContent
    layoutManager =  mLayoutManger
}

@BindingAdapter(value = ["setupFlexed","flexDirection","flexJustifyContent"], requireAll = false)
fun <T :RecyclerView.Adapter<*>> VeilRecyclerFrameView.setupFlexed(customAdapter:T,flexDirection: Int,flexJustifyContent: Int){
    val mLayoutManger = FlexboxLayoutManager(context)
    mLayoutManger.flexDirection = flexDirection
    mLayoutManger.justifyContent = flexJustifyContent
    setLayoutManager(mLayoutManger)
    setAdapter(customAdapter)
}


fun <D>RecyclerView.initiateDragHelper(arrayList: ArrayList<D>, onDrop : ((Int, Int) -> Unit) ? = null) : ItemTouchHelper {

    val itemTouchHelperCallBack : ItemTouchHelper.SimpleCallback  = object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN,0) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {

            val fromPosition = viewHolder.adapterPosition
            val toPosition = target.adapterPosition

            Collections.swap(arrayList,fromPosition,toPosition)

            adapter?.notifyItemMoved(fromPosition,toPosition)

            onDrop?.let { it(fromPosition,toPosition) }

            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

        }

    }

    return  ItemTouchHelper(itemTouchHelperCallBack)
}

fun RecyclerView.attachDragHelper(itemTouchHelper : ItemTouchHelper ){
    itemTouchHelper.attachToRecyclerView(this)
}

fun RecyclerView.setDivider(@DrawableRes drawableRes: Int) {
    val drawable = ContextCompat.getDrawable(
        this.context,
        drawableRes
    )
    val divider = DividerItemDecorator(
        drawable!!
    )
    addItemDecoration(divider)
}

fun RecyclerView.removeDragHelper(itemTouchHelper : ItemTouchHelper ){
    itemTouchHelper.attachToRecyclerView(null)
}


fun RecyclerView.removeItem(position: Int){
   adapter?.notifyItemRemoved(position)
   handler.postDelayed({
       adapter?.notifyDataSetChanged()
   },300)
}

fun RecyclerView.animateScrollTo(position: Int){
    val smoothScroller: RecyclerView.SmoothScroller = object : LinearSmoothScroller(context) {
        override fun calculateTimeForScrolling(dx: Int): Int {
            return 250
        }
        override fun getVerticalSnapPreference(): Int {
            return SNAP_TO_START
        }
    }
    smoothScroller.targetPosition = position
    layoutManager?.startSmoothScroll(smoothScroller)
}

fun <T : View>MaterialToolbar.getActionById(actionId : Int) : T {
    val actionsContainer = this.getChildAt(0) as ConstraintLayout
    Log.i("Toolbar","Container $actionsContainer")
    val view = findViewById<T>(actionId)
    view.visible()
    view.setOnClickListener {  }
    return view
}

fun View.fadeInAnimate(startDelay : Long = 0,animationDuration : Long){
    animate().alpha(1f).setStartDelay(startDelay).setDuration(animationDuration).start()
}

fun View.fadeOutAnimate(startDelay : Long = 0,animationDuration : Long){
    this.animate().alpha(0f).setStartDelay(startDelay).setDuration(animationDuration).start()
}

fun Fragment.showDialogSpinner(header:Int?,arrayList: ArrayList<Int>,isAlert:Boolean ?= false,initialSelection: Int? = null,onDismissed : (()-> Unit)?= null,onItemSelected: OnItemPressed){

    val stringArr : ArrayList<String> = ArrayList()
    stringArr.clear()
    arrayList.mapTo(stringArr,{s -> getString(s)})

    val dropDown = DropDownBottomSheet.init(_items = stringArr,_isAlert = isAlert, _initialSelected = initialSelection,_header = header?.let { return@let getString(it)}?:"" ,_onPress =  { pos -> onItemSelected(pos)},_onDismissCallBack = onDismissed)
    dropDown.show(childFragmentManager,"TAG")
    /*if(!dropDown.isAdded)
        dropDown.show(childFragmentManager,"TAG")*/

}

fun Fragment.showDialogSpinner(header:String,arrayList: ArrayList<String>,initialSelection: Int? = null,isAlert:Boolean ?= false,onDismissed : (()-> Unit)?= null,onItemSelected: OnItemPressed){

    val dropDown = DropDownBottomSheet.init(_items = arrayList,_isAlert = isAlert, _initialSelected = initialSelection,_header = header,_onPress =  { pos -> onItemSelected(pos)},_onDismissCallBack = onDismissed)
    dropDown.show(childFragmentManager,"TAG")

    /*if(!dropDown.isAdded)
        dropDown.show(childFragmentManager,"TAG")*/

}


fun Fragment.showOptionsDialog(header:String ? = null ,
                               confirmBtnText:String?=null,
                               arrayList: ArrayList<String>,
                               initialSelection : Int ?= 0 ,
                               withoutCheckBox:Boolean = false,
                               searchEnabled:Boolean = false,
                               onItemSelected: OnItemPressed
                               ){

    val dropDown = OptionsBottomSheet.init(_header = header,_confirmBtn = confirmBtnText,arrayList,_initialSelection = initialSelection,_withoutCheckBox = withoutCheckBox,_searchEnabled = searchEnabled){ pos ->
        onItemSelected(pos)
    }.show(childFragmentManager,"TAG")

}

fun Animation.reverse(): Animation {
    this.interpolator = Interpolator { p0 -> abs(p0 - 1f) }
    return this
}

fun TextInputLayout.setBorderStableColor(color:Int) {
    setBoxStrokeColorStateList(context.makeCustomTintColorStateList(Constants.txtFieldStates,color))
}

fun Context.changeVectorColor(imageView:ImageView,drawableId:Int,themeRes:Int) {
    val wrapper = ContextThemeWrapper(this, themeRes)
    val drawable = ResourcesCompat.getDrawable(resources, drawableId, wrapper.theme)
    imageView.setImageDrawable(drawable)
}

fun Context.createATheme(hexColor : String) : Int {
    return resources.getIdentifier("T_$hexColor", "style", packageName)
}

fun View.clickWithHideKeyboard(fragment: Fragment){
    setOnClickListener {
        fragment.requireActivity().closeKeyboard()
    }
}
//fun ImageView.loadThemeToDrawable(themeRes: Int,drawableRes: Int){
//    val theme = ContextThemeWrapper(context, themeRes)
//    GlideApp.with(context)
//        .load(ResourcesCompat.getDrawable(resources, drawableRes, theme.theme))
//        .placeholder(R.drawable.ic_class_bg)
//        .into(this)
//}