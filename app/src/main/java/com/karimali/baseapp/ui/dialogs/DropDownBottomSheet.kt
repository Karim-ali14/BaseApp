package com.karimali.baseapp.ui.dialogs

import android.os.Build
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ImageSpan
import android.view.Gravity
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.karimali.baseapp.R
import com.karimali.baseapp.common.extensions.setDivider
import com.karimali.baseapp.common.extensions.visible
import com.karimali.baseapp.common.models.DialogSpinnerItem
import com.karimali.baseapp.databinding.DropdownBottomSpinnerItemsBinding
import com.karimali.baseapp.databinding.SpinnerItemLayoutBinding
import com.karimali.baseapp.ui.adapters.GenericRecyclerAdapter
import com.karimali.baseapp.ui.adapters.GenericSimpleRecyclerBindingInterface
import com.karimali.baseapp.ui.adapters.OnPressedInterface
import com.karimali.baseapp.ui.base.RoundedBottomSheetDialogFragment


class DropDownBottomSheet : RoundedBottomSheetDialogFragment<DropdownBottomSpinnerItemsBinding>(R.layout.dropdown_bottom_spinner_items) {


    companion object {

        private var spinnerItems : ArrayList<DialogSpinnerItem> ?= null
        private var initialSelected : Int ?= null
        private var items : ArrayList<String> ?= null
        private var isAlert : Boolean ?= false
        private var header:String ?= null
        private var onPress : ((Int) -> Unit) = {}
        private var onDismissCallBack : (() -> Unit) ?= null

        fun init(_items:ArrayList<String>? = null,
                 _spinnerItems:ArrayList<DialogSpinnerItem>? = null,
                 _isAlert : Boolean ?= false,
                 _header:String ?= null,
                 _initialSelected : Int ?= null,
                 _onDismissCallBack : (() -> Unit)? = null,
                 _onPress : (Int) -> Unit,
        ) : DropDownBottomSheet {

            spinnerItems = _spinnerItems
            items = _items
            isAlert = _isAlert
            header = _header
            initialSelected = _initialSelected
            onPress = _onPress
            onDismissCallBack = _onDismissCallBack
            return DropDownBottomSheet()
        }

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = DropdownBottomSpinnerItemsBinding.bind(view)

        super.onViewCreated(view, savedInstanceState)

        val stringItems : ArrayList<String> = ArrayList()
        stringItems.clear()


        val itemsAdapter = if(spinnerItems == null)
        {
            GenericRecyclerAdapter<String>(
                items!!,
                R.layout.spinner_item_layout,
                adapterBinding,
                adapterBindingOnPress
            )
        }
        else{
            GenericRecyclerAdapter<DialogSpinnerItem>(
                spinnerItems!!,
                R.layout.spinner_item_layout,
                adapterSpinnerItemsBinding,
                adapterSpinnerIconBindingOnPress
            )
        }


        binding!!.apply {
            header?.let {
                binding!!.headerText.visible()
            }
            headerText.text = header
            val headerColor = if(isAlert == true) R.color.red else android.R.color.black
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                headerText.setTextColor(requireContext().getColor(headerColor))
            }
            itemsRv.apply {
                adapter = itemsAdapter
                layoutManager = LinearLayoutManager(binding!!.root.context)
            }
            itemsRv.setDivider(R.drawable.recycler_view_divider)
        }

        initialSelected?.let {

        }

        requireDialog().setOnDismissListener {
            onDismissCallBack?.let { it() }
            super.onDismiss(it)
        }
    }

    private val adapterBinding = object  : GenericSimpleRecyclerBindingInterface<String> {
        override fun bindData(item: String, view: View,position: Int?) {
            val binding = SpinnerItemLayoutBinding.bind(view)
            binding.itemTxt.text = item
            if(initialSelected == position){
                val text = "   $item    "
                val sb : SpannableStringBuilder = SpannableStringBuilder(text)
                val imageSpan : ImageSpan = ImageSpan(binding.root.context, R.drawable.ic_green_check)
                sb.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.itemTxt.text = sb
                //binding.itemTxt.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_checked_item_circle,0,0,0)
            }else{
                binding.itemTxt.text = item
            }
        }
    }

    private val adapterSpinnerItemsBinding = object  : GenericSimpleRecyclerBindingInterface<DialogSpinnerItem>{
        override fun bindData(item: DialogSpinnerItem, view: View,position: Int?) {
            val binding = SpinnerItemLayoutBinding.bind(view)
            binding.itemTxt.apply {
                val itemColor = item.color?.let {
                    ContextCompat.getColorStateList(binding.root.context,it)
                }
                TextViewCompat.setCompoundDrawableTintList(this,itemColor)
                gravity = Gravity.START or Gravity.CENTER_VERTICAL
                text = item.name
                setCompoundDrawablesRelativeWithIntrinsicBounds(item.icon, 0, 0, 0)
            }
        }
    }

    private val adapterBindingOnPress = object  : OnPressedInterface<String> {
        override fun onPressedWithPos(item: Any?,pos:Int) {
            onPress(pos)
            dismiss()
        }
    }

    private val adapterSpinnerIconBindingOnPress = object  : OnPressedInterface<DialogSpinnerItem> {
        override fun onPressedWithPos(item: Any?,pos:Int) {
            onPress(pos)
            dismiss()
        }
    }


}