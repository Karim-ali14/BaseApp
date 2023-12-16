package com.karimali.baseapp.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.ArrayList

class GenericRecyclerAdapter<T :Any?> @JvmOverloads constructor(
    val dataSet: ArrayList<T>,
    @LayoutRes val layoutID: Int,
    private val bindingInterface: GenericSimpleRecyclerBindingInterface<T>?= null,
    private val onItemPressedCallBack: OnPressedInterface<T>?= null,
    private val bindingInterface2: GenericSimpleRecyclerBindingInterface2<T>?= null,
    private val bindingInterface3: GenericSimpleRecyclerBindingInterface3<T>?= null,
) : RecyclerView.Adapter<GenericRecyclerAdapter<T>.ViewHolder>() {
    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutID, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataSet[position]

        bindingInterface?.bindData(item,holder.view,position)
        bindingInterface2?.bindData(item,holder.view,holder.itemView,position)
        bindingInterface3?.bindData(item,holder.view,position,dataSet.size)

        onItemPressedCallBack?.let { onPress ->
            holder.itemView.setOnClickListener {
                onPress.onPressed(item)
                onPress.onPressedWithPos(item,position)
            }
        }
    }

    override fun getItemCount(): Int = dataSet.size

    fun updateDate(data: ArrayList<T>){
        dataSet.clear()
        dataSet.addAll(data)
        notifyDataSetChanged()
    }

    fun updateData(data: List<T>?){
        data?.let {
            dataSet.clear()
            dataSet.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun updateDateAnimated(data: List<T>){
        val dataSize = dataSet.size
        dataSet.clear()
        dataSet.addAll(data)
        notifyItemRangeRemoved(0,dataSize)
        notifyItemRangeInserted(0,dataSet.size)
    }

    fun <B : T>updateDateGeneric(data: ArrayList<B>){
        dataSet.clear()
        dataSet.addAll(data)
        notifyDataSetChanged()
    }

    fun updateDate(data: List<T>){
        dataSet.clear()
        dataSet.addAll(data)
        notifyDataSetChanged()
    }

    fun addNewDate(data: List<T>){
//        dataSet.clear()
        dataSet.addAll(data)
        notifyDataSetChanged()
    }

    fun deleteItem(index:Int){
        Log.i("deleteItem","$index ${dataSet.size}")
        dataSet.removeAt(index)
        notifyItemRemoved(index)
        Log.i("deleteItem","$index ${dataSet.size}")
    }

    fun <A>deleteByPredicate(item : A){
        val pos = dataSet.indexOfFirst { obj ->  obj == item}
        if(pos != -1){
            dataSet.removeAt(pos)
            notifyItemRemoved(pos)
        }
    }

}


interface GenericSimpleRecyclerBindingInterface<T:Any?> {
    fun bindData(item: T,view:View,position: Int ?= 0){}
}

interface GenericSimpleRecyclerBindingInterface2<T:Any?> {
    fun bindData(item: T,view:View,itemView:View,position: Int ?= 0){}
}
interface GenericSimpleRecyclerBindingInterface3<T:Any?> {
    fun bindData(item: T,view:View,position: Int ?= 0,itemSize: Int? =0){}
}


interface OnPressedInterface<T:Any?> {
    fun onPressed(item: Any?){}
    fun onPressedWithPos(item: Any?,pos:Int){}
}
