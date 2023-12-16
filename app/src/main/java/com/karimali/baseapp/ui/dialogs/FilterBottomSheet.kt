package com.karimali.baseapp.ui.dialogs

//import android.os.Bundle
//import android.util.Log
//import android.view.View
//import android.widget.RadioGroup
//import androidx.core.widget.doOnTextChanged
//import androidx.databinding.DataBindingUtil
//import com.pomac.hajastclient.R
//import com.pomac.hajastclient.data.model.FilterModel
//import com.pomac.hajastclient.data.model.SelectorItem
//import com.pomac.hajastclient.data.model.SelectorItemWithTextAndIcon
//import com.pomac.hajastclient.databinding.FilterDialogBottomsheetBinding
//import com.pomac.hajastclient.databinding.OptionsTextWithIconDialogBottomsheetBinding
//import com.pomac.hajastclient.databinding.RadioItemBinding
//import com.pomac.hajastclient.databinding.RadioItemLayoutBinding
//import com.pomac.hajastclient.ui.base.RoundedBottomSheetDialogFragment
//import com.pomac.hajastclient.shared.utils.helper.extensions.*
//import com.pomac.hajastclient.ui.adapters.AdapterBindings
//import com.pomac.hajastclient.ui.adapters.loadImage
//import com.pomac.teacherpackage.shared.ui.adapters.GenericRecyclerAdapter
//import com.pomac.teacherpackage.shared.ui.adapters.GenericSimpleRecyclerBindingInterface
//import com.karimali.baseapp.common.extensions.showErrorToast
//import com.pomac.teacherpackage.shared.utils.helper.extensions.textChanges
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.flow.debounce
//import kotlinx.coroutines.flow.launchIn
//import kotlinx.coroutines.flow.onEach
//import java.lang.ref.WeakReference
//import kotlin.collections.ArrayList
//
//class FilterBottomSheet: RoundedBottomSheetDialogFragment
//<FilterDialogBottomsheetBinding>(R.layout.filter_dialog_bottomsheet) {
//    var serviceAdapter:GenericRecyclerAdapter<SelectorItem> = GenericRecyclerAdapter(
//        services?: arrayListOf(),
//        R.layout.radio_item_layout,
//        optionSelectorItemBinding(::handleSelectServiceItem)
//    )
//    var serviceProviderGenderAdapter:GenericRecyclerAdapter<SelectorItem> = GenericRecyclerAdapter(
//        genders?: arrayListOf(),
//        R.layout.radio_item_layout,
//        optionSelectorItemBinding(::handleSelectServiceProviderGenderItem)
//    )
//    private fun handleSelectServiceProviderGenderItem(item: SelectorItem) {
//        initialServiceGenderSelection = item
//        genders?.forEach {
//            Log.i("handleSelectGenderItem","${it.id} ${item.id}")
//            it.isCheck = it.id == item.id
//        }
//        serviceProviderGenderAdapter.notifyDataSetChanged()
//    }
//    private fun handleSelectServiceItem(item: SelectorItem) {
//        initialServiceSelection = item
//        services?.forEach {
//            Log.i("handleSelectItem","${it.id} ${item.id}")
//            it.isCheck = it.id == item.id
//        }
//        serviceAdapter.notifyDataSetChanged()
//    }
//
//
//    var initialServiceSelection:SelectorItem ?= null
//    var initialServiceGenderSelection:SelectorItem ?= null
//
//    companion object {
//        var header : String ?= null
//        var confirmBtn : String ?= null
//        var services:ArrayList<SelectorItem> ?= null
//        private var tempservices:ArrayList<SelectorItem> = ArrayList()
//        var genders:ArrayList<SelectorItem> ?= null
//        private var tempGenders:ArrayList<SelectorItem> = ArrayList()
//        var initialSelection: FilterModel?= null
//        var onSelect : (FilterModel?) -> Unit = {}
//        var _binding : WeakReference<FilterDialogBottomsheetBinding> ?= null
//
//        private val _params = RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT,RadioGroup.LayoutParams.WRAP_CONTENT)
//
//
//        fun init(_header : String ?= null, _confirmBtn : String ?= null, _services:ArrayList<SelectorItem>, _gender:ArrayList<SelectorItem>, _initialSelection:FilterModel ?= null ,_initialServiceSelection:SelectorItem ?= null, _onSelect : (FilterModel?) -> Unit) : FilterBottomSheet {
//            header = _header
//            confirmBtn = _confirmBtn
//            services = _services
//            genders = _gender
//            tempservices.clear()
//            services?.mapTo(tempservices){ it }
//            genders?.mapTo(tempGenders){ it }
//            initialSelection = _initialSelection
//            onSelect = _onSelect
//            return FilterBottomSheet()
//        }
//    }
//
//    private val params = RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT,RadioGroup.LayoutParams.WRAP_CONTENT)
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        binding = FilterDialogBottomsheetBinding.bind(view)
//        super.onViewCreated(view, savedInstanceState)
//
//
//        setInitDate()
//
//        checkEmptyDate()
//
//        _binding = WeakReference(binding)
//
//        Log.i("Search Result","8"+ _binding.toString())
//
//        binding!!.apply {
//
//
//            header?.let {
//                headerText.text = it
//                headerText.visible()
//            }
//
//            confirmBtn?.let {
//                doneBtn.visible()
//                doneBtn.text = it
//            }
//
//            servicesRy.setup(serviceAdapter)
//            serviceProvidersGenderRy.setup(serviceProviderGenderAdapter)
//
//            doneBtn.setOnClickListener {
//                onSelect(getFilterDate())
//            }
//
//            resetText.setOnClickListener {
//                resetAllDate()
//            }
//
//        }
//
//        isFullyExpanded()
//    }
//
//    private fun setInitDate() {
//        binding!!.apply {
//            initialSelection?.let { initItem ->
//                if (initItem.selectedService != null && !services.isNullOrEmpty()){
//                    Log.i("setInitDate Service","not null")
//                    val serviceSelectedItem = services?.get(services!!.indexOfFirst { item -> item.id == initItem.selectedService?.id })
//                    serviceSelectedItem?.let { handleSelectServiceItem(it) }
//                }else{
//                    handleSelectServiceItem(SelectorItem(id = -1))
//                }
//                if (initItem.selectedGender != null && !genders.isNullOrEmpty()){
//                    val genderSelectedItem = genders?.get(genders!!.indexOfFirst { item -> item.id == initItem.selectedGender?.id })
//                    genderSelectedItem?.let { handleSelectServiceProviderGenderItem(it) }
//                }else {
//                    handleSelectServiceProviderGenderItem(SelectorItem(id = -1))
//                }
//                if (initItem.priceFrom != null && initItem.priceTo != null){
//                    packagePriceFrom.setText(initItem.priceFrom.toString())
//                    packagePriceTo.setText(initItem.priceTo.toString())
//                }else{
//                    packagePriceFrom.setText("")
//                    packagePriceTo.setText("")
//                }
//            }
//        }
//
//    }
//
//    private fun getFilterDate():FilterModel? {
//        binding!!.apply {
//            initialServiceSelection?.let { item ->
//                if(services?.isNotEmpty() == true){
//                    val itemIndex = services?.indexOfFirst { obj -> item.id == obj.id }
//                    if (itemIndex != null && itemIndex != -1) {
//                        initialSelection?.selectedService = services?.get(itemIndex)
//                    }
//                }
//            }
//            initialServiceGenderSelection?.let { item ->
//                if(genders?.isNotEmpty() == true){
//                    val itemIndex = genders?.indexOfFirst { obj -> item.id == obj.id }
//
//                    if (itemIndex != null && itemIndex != -1) {
//                        initialSelection?.selectedGender = genders?.get(itemIndex)
//                    }
//                }
//            }
//
//            if (packagePriceFrom.text.toString().isNotEmpty() || packagePriceTo.text.toString().isNotEmpty()){
//                if (packagePriceFrom.isEmptyFieldValidation() && packagePriceTo.isEmptyFieldValidation()){
//                    if (packagePriceFrom.isNumberGreaterThan(packagePriceTo.text.toString().toInt())){
//                        initialSelection?.priceFrom = packagePriceFrom.text.toString().toInt()
//                        initialSelection?.priceTo = packagePriceTo.text.toString().toInt()
//                        dismiss()
//                    }
//                }
//            }else{
//                initialSelection?.priceFrom = null
//                initialSelection?.priceTo = null
//                dismiss()
//            }
//        }
//        return initialSelection
//    }
//
//    private fun checkEmptyDate() {
//        binding!!.apply {
//            serviceLy.visibility = if (services.isNullOrEmpty()) View.GONE else View.VISIBLE
//        }
//    }
//
//    private fun optionSelectorItemBinding(onPress: (SelectorItem) -> Unit) =
//        object : GenericSimpleRecyclerBindingInterface<SelectorItem> {
//            override fun bindData(item: SelectorItem, view: View,position: Int?) {
//                DataBindingUtil.bind<RadioItemLayoutBinding>(view)?.apply {
//                    title.text = item.name
//                    radio.isChecked = item.isCheck ?: false
//                    root.setOnClickListener {
//                        onPress(item)
//                    }
//                    if (services?.lastIndex == position){
//                        line.visibility = View.GONE
//                    }else {
//                        line.visibility = View.VISIBLE
//                    }
//                }
//            }
//        }
//
//    private fun resetAllDate(){
//        services?.forEach {
//            it.reset()
//        }
//        genders?.forEach {
//            it.reset()
//        }
//        initialServiceSelection = null
//        initialServiceGenderSelection = null
//        serviceProviderGenderAdapter.notifyDataSetChanged()
//        serviceAdapter.notifyDataSetChanged()
//        binding!!.packagePriceFrom.setText("")
//        binding!!.packagePriceTo.setText("")
//        initialSelection = FilterModel()
//    }
//}