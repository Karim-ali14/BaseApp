package com.karimali.baseapp.di

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


@Suppress("UnstableApiUsage")
class AppSharedPrefs @Inject constructor(@ApplicationContext context : Context) {

    private val prefs = context.getSharedPreferences("preferences_name", Context.MODE_PRIVATE)

    fun getData(key:String): String {
        return prefs.getString(key, "")!!
    }

    inline fun <reified T>getSavedData(key:String): T? {
        val json = getData(key)
        if(json.isNotEmpty()){
            return Gson().fromJson(json, T::class.java) as T
        }
        return null
    }

    inline fun <reified T>getSavedDataList(key:String): ArrayList<T>? {
        val json = getData(key)
        if(json.isNotEmpty()){
            return Gson().fromJson<ArrayList<T>>(json, object : TypeToken<ArrayList<T>>(){}.type)
        }
        return null
    }


    fun getBool(key:String): Boolean {
        return prefs.getBoolean(key, true)
    }

    fun setBool(key:String,bool:Boolean) {
        Log.i("Landing","$bool")
         prefs.edit().putBoolean(key,bool).apply()
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return prefs.getInt(key, defaultValue)
    }

    fun setInt(key:String,int:Int) {
        prefs.edit().putInt(key,int).apply()
    }

    fun storeData(key:String, data: Any) {
        val json = Gson().toJson(data)
        Log.i("SharedPrefs","Data Saved : $json")
        prefs.edit().putString( key , json ).apply()
    }

    fun storeList(key:String, data: Set<String>){
        Log.i("SharedPrefs","Data Saved : $data")
        prefs.edit().putStringSet(key,data).apply()
    }

    fun getList(key:String) : Set<String>?{
        return  prefs.getStringSet(key, setOf())
    }

    fun clearData(key:String){
        prefs.edit().remove(key).apply()
    }

}