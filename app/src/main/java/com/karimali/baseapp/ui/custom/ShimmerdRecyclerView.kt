package com.karimali.baseapp.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.JustifyContent
import com.karimali.baseapp.R
import com.karimali.baseapp.common.extensions.setup
import com.karimali.baseapp.common.extensions.setupFlexed
import com.karimali.baseapp.common.extensions.setupStaggered
import com.karimali.baseapp.databinding.ShimmerdRecyclerViewBinding
import com.karimali.baseapp.ui.adapters.AdapterBindings
import com.karimali.baseapp.ui.adapters.GenericRecyclerAdapter

class ShimmerdRecyclerView<in T> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var root : View = LayoutInflater.from(context).inflate(R.layout.shimmerd_recycler_view,this,true)!!
    private lateinit var shimmerAdapter : GenericRecyclerAdapter<Any?>
    private var selectedType : RecyclerLayoutTypes = RecyclerLayoutTypes.Linear
    private lateinit var  customAdapter : RecyclerView.Adapter<*>

    private val rootBinding by lazy {
        ShimmerdRecyclerViewBinding.bind(root)
    }


    init {
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.ShimmerdRecyclerView, defStyleAttr, 0
        )
        a.recycle()
    }

    fun addAdapter(adapter: GenericRecyclerAdapter<out T>,recyclerType : RecyclerLayoutTypes,cols : Int ? = 2 , shimmerLayoutRes : Int ?= null)  {
        selectedType = recyclerType
        customAdapter = adapter
        shimmerLayoutRes?.let { res ->
            shimmerAdapter = GenericRecyclerAdapter(
                arrayListOf(null,null,null,null,null,null),
                res,
                AdapterBindings.shimmerBinding()
            )
        }

        rootBinding.apply {
            when(selectedType){
                RecyclerLayoutTypes.Linear -> rv.setup(adapter, isLinear = true)
                RecyclerLayoutTypes.LinearHorizontal -> rv.setup(adapter, isHorizontal = true)
                RecyclerLayoutTypes.Grid -> rv.setup(adapter, cols = cols , isLinear = false)
//                RecyclerLayoutTypes.Staggered -> rv.setupStaggered(adapter,cols)
                RecyclerLayoutTypes.FlexedColumn -> rv.setupFlexed(adapter,FlexDirection.COLUMN,JustifyContent.FLEX_START)
                RecyclerLayoutTypes.FlexedRow -> rv.setupFlexed(adapter,FlexDirection.ROW,JustifyContent.FLEX_START)
            }
        }
    }
    fun addAdapter(adapter: RecyclerView.Adapter<*>,recyclerType : RecyclerLayoutTypes,cols : Int ? = 2 , shimmerLayoutRes : Int ?= null)  {
        selectedType = recyclerType
        customAdapter = adapter
        shimmerLayoutRes?.let { res ->
            shimmerAdapter = GenericRecyclerAdapter(
                arrayListOf(null,null,null,null,null,null),
                res,
                AdapterBindings.shimmerBinding()
            )
        }

        rootBinding.apply {
            when(selectedType){
                RecyclerLayoutTypes.Linear -> rv.setup(adapter, isLinear = true)
                RecyclerLayoutTypes.LinearHorizontal -> rv.setup(adapter, isHorizontal = true)
                RecyclerLayoutTypes.Grid -> rv.setup(adapter, cols = cols , isLinear = false)
//                RecyclerLayoutTypes.Staggered -> rv.setupStaggered(adapter,cols)
                RecyclerLayoutTypes.FlexedColumn -> rv.setupFlexed(adapter,FlexDirection.COLUMN,JustifyContent.FLEX_START)
                RecyclerLayoutTypes.FlexedRow -> rv.setupFlexed(adapter,FlexDirection.ROW,JustifyContent.FLEX_START)
            }
        }
    }

    fun shimmered(shimmerEnabled:Boolean) {
        if(shimmerEnabled){
            rootBinding.apply {
                when(selectedType){
                    RecyclerLayoutTypes.Linear -> rv.setup(shimmerAdapter, isLinear = true)
                    RecyclerLayoutTypes.Grid -> rv.setup(shimmerAdapter , cols = 2 , isLinear = false)
                    RecyclerLayoutTypes.LinearHorizontal -> rv.setup(shimmerAdapter, isHorizontal = true)
//                    RecyclerLayoutTypes.Staggered -> rv.setupStaggered(shimmerAdapter ,2)
                    RecyclerLayoutTypes.FlexedColumn -> rv.setupFlexed(shimmerAdapter,FlexDirection.COLUMN,JustifyContent.FLEX_START)
                    RecyclerLayoutTypes.FlexedRow -> rv.setupFlexed(shimmerAdapter,FlexDirection.ROW,JustifyContent.FLEX_START)
                }
            }
        }else{
            rootBinding.apply {
                when(selectedType){
                    RecyclerLayoutTypes.Linear -> rv.setup(customAdapter , isLinear = true)
                    RecyclerLayoutTypes.LinearHorizontal -> rv.setup(customAdapter, isHorizontal = true)
                    RecyclerLayoutTypes.Grid -> rv.setup(customAdapter , cols = 2 , isLinear = false)
//                    RecyclerLayoutTypes.Staggered -> rv.setupStaggered(customAdapter as GenericRecyclerAdapter<T>,2)
                    RecyclerLayoutTypes.FlexedColumn -> rv.setupFlexed(customAdapter ,FlexDirection.COLUMN,JustifyContent.FLEX_START)
                    RecyclerLayoutTypes.FlexedRow -> rv.setupFlexed(customAdapter ,FlexDirection.ROW,JustifyContent.FLEX_START)
                }
            }
        }
    }

    val getRecyclerView : RecyclerView get() = rootBinding.rv
}

enum class RecyclerLayoutTypes {
    Linear,
    LinearHorizontal,
    Grid,
    FlexedColumn,
    FlexedRow,
//    Staggered
}