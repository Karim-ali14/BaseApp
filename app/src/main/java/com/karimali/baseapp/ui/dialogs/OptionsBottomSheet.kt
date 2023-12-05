package com.karimali.baseapp.ui.dialogs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.karimali.baseapp.R
import com.karimali.baseapp.common.extensions.textChanges
import com.karimali.baseapp.common.extensions.toDp
import com.karimali.baseapp.databinding.OptionsDialogBottomsheetBinding
import com.karimali.baseapp.ui.base.RoundedBottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.lang.ref.WeakReference
import kotlin.collections.ArrayList

class OptionsBottomSheet: RoundedBottomSheetDialogFragment<OptionsDialogBottomsheetBinding>(R.layout.options_dialog_bottomsheet) {

    init {

    }
    companion object {
        var header : String ?= null
        var confirmBtn : String ?= null
        var options:ArrayList<String> ?= null
        private var tempOptions:ArrayList<String> = ArrayList()
        var initialSelection:Int ?= null
        var withoutCheckBox:Boolean ?= null
        var searchEnabled:Boolean ?= null
        var onSelect : (Int) -> Unit = {}
        var _binding : WeakReference<OptionsDialogBottomsheetBinding> ?= null

        private val _params = RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT,RadioGroup.LayoutParams.WRAP_CONTENT)


        fun init(_header : String ?= null, _confirmBtn : String ?= null, _options:ArrayList<String>, _initialSelection:Int ?= 0,_withoutCheckBox:Boolean,_searchEnabled:Boolean, _onSelect : (Int) -> Unit) : OptionsBottomSheet {
            header = _header
            confirmBtn = _confirmBtn
            options = _options
            tempOptions.clear()
            options?.mapTo(tempOptions){ it }
            initialSelection = _initialSelection
            withoutCheckBox = _withoutCheckBox
            searchEnabled = _searchEnabled
            onSelect = _onSelect
            return OptionsBottomSheet()
        }

        fun handleSearch(scope : CoroutineScope , onChange : (String) -> Unit){
            Log.i("Search Result", _binding?.get().toString())
            _binding?.get()?.search?.textChanges()?.debounce(500)?.onEach {
                Log.i("Search Result",it.toString())
                onChange(it.toString())
            }?.launchIn(scope)
        }

        fun refreshData(tempOptions:ArrayList<String>) {
            _binding?.get()?.apply {
                options?.clear()
                options?.addAll(tempOptions)
                radioGroup.removeAllViews()
                options?.map { radioGroup.addView(button(it)) }
//                doneBtn.toggleEnabled(options?.isNotEmpty() == true)
            }
        }

        private fun button(name:String) : RadioButton{
            val radio = if(withoutCheckBox == true){
                (LayoutInflater.from(_binding!!.get()?.root?.context).inflate(R.layout.checked_text_item_layout,_binding!!.get()?.radioGroup,false) as RadioButton).apply {
                    text = name
                    _params.setMargins(_binding!!.get()?.root?.context?.toDp(125) ?: 0,0,_binding!!.get()?.root?.context?.toDp(125) ?: 0,0)
                    layoutParams = _params
                }
            }else{
                (LayoutInflater.from(_binding!!.get()?.root?.context).inflate(R.layout.radio_item,_binding!!.get()?.radioGroup,false) as RadioButton).apply {
                    //radio.textAlignment = View.TEXT_ALIGNMENT_CENTER
                    text = name
                    _params.setMargins(0,_binding!!.get()?.root?.context?.toDp(100) ?: 0,0,_binding!!.get()?.root?.context?.toDp(100) ?: 0)
                    layoutParams = _params
                }
            }
            return radio
        }
    }

    private val params = RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT,RadioGroup.LayoutParams.WRAP_CONTENT)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = OptionsDialogBottomsheetBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)


        _binding = WeakReference(binding)

        Log.i("Search Result","8"+ _binding.toString())

        binding!!.apply {

            search.isVisible(searchEnabled == true)

            header?.let {
                headerText.text = it
                headerText.visible()
            }

            confirmBtn?.let {
                doneBtn.visible()
                doneBtn.text = it
            }

            options?.map { radioGroup.addView(button(it)) }

            try {
                if(initialSelection != null)
                {
                    (radioGroup[initialSelection!!] as RadioButton).isChecked = true
                }
            } catch (e:Exception){}


            radioGroup.setOnCheckedChangeListener { radioGroup, i ->
                val index = radioGroup.indexOfChild(radioGroup.findViewById(i))
                initialSelection = index
            }


            doneBtn.setOnClickListener {
                dismiss()
                initialSelection?.let {
                    if(searchEnabled == true && options?.isNotEmpty() == true){
                        onSelect(tempOptions.indexOfFirst { obj -> (options?.get(it) ?: "").contains(obj,true)  })
                    }else if(searchEnabled == true && options?.isEmpty() == true){
                        showErrorToast("Select")
                    } else{
                        onSelect(it)
                    }
                }
            }

            if(searchEnabled == true){
                radioGroup.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
                search.doOnTextChanged { text, start, before, count ->
                    options?.clear()
                    radioGroup.removeAllViews()
                    tempOptions.filter { obj -> obj.contains(text.toString(),ignoreCase = true) }.toCollection(options!!)
                    options?.map { radioGroup.addView(button(it) as RadioButton) }
//                    doneBtn.toggleEnabled(options?.isNotEmpty() == true)
                }
            }
        }

        isFullyExpanded()
    }

    private fun button(item: Pair<String?, String>) : View{
        val radio = if(withoutCheckBox == true){
            (LayoutInflater.from(requireContext()).inflate(R.layout.checked_text_item_layout,binding!!.radioGroup,false) as RadioButton).apply {
                text = item.second
                params.setMargins(requireContext().toDp(125),0,requireContext().toDp(125),0)
                layoutParams = params
            }
        } else{
            (LayoutInflater.from(requireContext()).inflate(R.layout.radio_item,binding!!.radioGroup,false) as RadioButton).apply {
                //radio.textAlignment = View.TEXT_ALIGNMENT_CENTER
                text = item.second
                params.setMargins(0,requireContext().toDp(100),0,requireContext().toDp(100))
                layoutParams = params
            }
        }
        return radio
    }


}