package com.karimali.baseapp.common.extensions

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.androidbuffer.kotlinfilepicker.KotConstants
import com.androidbuffer.kotlinfilepicker.KotRequest
import com.androidbuffer.kotlinfilepicker.KotResult
import com.karimali.baseapp.common.utils.Constants
import com.karimali.baseapp.common.utils.Constants.FilePickerConst.REQUEST_CODE_DOC
import com.karimali.baseapp.common.utils.Constants.FilePickerConst.REQUEST_CODE_PHOTO
import java.io.File
import java.util.ArrayList
import kotlin.random.Random


typealias PickSelectionCallback = (requestCode:Int, resultCode:Int,data: Intent?) -> Unit

fun Activity.pickFiles(fileType : String? = KotConstants.FILE_TYPE_FILE_ALL, isMultiPicker:Boolean ?= true) {
    KotRequest.File(this, REQUEST_CODE_DOC)
        .isMultiple(isMultiPicker?:true)
        .setMimeType(fileType?:KotConstants.FILE_TYPE_FILE_ALL)
        .pick()
}

fun Activity.pickImages(fileType : String?,isMultiPicker:Boolean ?= true,requestCode: Int = REQUEST_CODE_PHOTO) {
    KotRequest.File(this, requestCode)
        .isMultiple(isMultiPicker?:true)
        .setMimeType(fileType?:KotConstants.FILE_TYPE_IMAGE_ALL)
        .pick()
}

fun Activity.handlingFilePickingDataSingle(requestCode : Int , resultCode : Int,data: Intent?): String? {
    Log.i("Data","Files $requestCode")
    when(requestCode){
        REQUEST_CODE_DOC, REQUEST_CODE_PHOTO , Constants.FilePickerConst.REQUEST_CODE_CAVER_PHOTO, Constants.FilePickerConst.REQUEST_CODE_PROFILE_PHOTO -> if (resultCode == Activity.RESULT_OK && data != null) {
            return try{
                val result2 = data.getParcelableArrayListExtra<KotResult>(KotConstants.EXTRA_FILE_RESULTS)
                Log.i("Data","Files $result2")
                val result = data.getParcelableArrayListExtra<KotResult>(KotConstants.EXTRA_FILE_RESULTS)

                this.contentResolver.takePersistableUriPermission(
                    result?.first()!!.uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )
                val file = result.first().uri
                file.copyFileToInternalStorage(
                    this,
                    "Hajast${Random.nextInt(999999)}-${File(file.path!!).extension}"
                )
            }catch (e: Exception){
                Log.i("File","Err ${e.message}")
                null
            }

        }
    }
    return null
}

fun Activity.handlingFilePickingDataMulti(requestCode : Int , resultCode : Int,data: Intent?): ArrayList<String>? {
    val uris : ArrayList<String> = ArrayList()
    when(requestCode){
        REQUEST_CODE_DOC, REQUEST_CODE_PHOTO , Constants.FilePickerConst.REQUEST_CODE_CAVER_PHOTO, Constants.FilePickerConst.REQUEST_CODE_PROFILE_PHOTO -> if (resultCode == Activity.RESULT_OK && data != null) {
            return try{
                val result2 = data.getParcelableArrayListExtra<KotResult>(KotConstants.EXTRA_FILE_RESULTS)
                Log.i("Data","Files $result2")
                val result = data.getParcelableArrayListExtra<KotResult>(KotConstants.EXTRA_FILE_RESULTS)
                result!!.map { res ->
                    this.contentResolver.takePersistableUriPermission(
                        res.uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    )

                    Log.i("File","IsExist ${res.uri.path?.let { File(it).mkdir() }}")
                    uris.add(res.uri.copyFileToInternalStorage(
                        this,
                        "Hajast${Random.nextInt(999999)}-${File(res.uri.path!!).extension}"
                    ))
                }
                return uris
            }catch (e: Exception){
                Log.i("File","Err ${e.message}")
                null
            }

        }
    }
    return null
}
